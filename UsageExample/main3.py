
# Module to work with Python 3
import os
import subprocess
import tempfile
import sys

QUIET = False

class VersionCheckerError(Exception):
    def __init__(self, message=""):
        self.message = message
    def __str__(self):
        return "VersionCheckerError: " + self.message

class ApiGeneratorError(Exception):
    def __init__(self, message=""):
        self.message = message
    def __str__(self):
        return "ApiGeneratorError: " + self.message

class GitError(Exception):
    def __init__(self, message=""):
        self.message = message
    def __str__(self):
        return "GitError: " + self.message

def call_version_checker(old_path, new_path, output):
    args = ["java",
            "-jar",
            os.path.join("libs", "VersionChecker-1.0.0.jar"),
            "--output", output,
            old_path,
            new_path]
    r = subprocess.run(args)
    if r.returncode != 0:
        raise VersionCheckerError("Error calling VersionChecker:\n" +
                str(r.stdout))

def call_api_generator(repo_path, output_path):
    args = ["java",
            "-jar",
            os.path.join("libs", "ModelicaPublicApiGenerator-1.0.0.jar"),
            "--quiet",
            "--modelicapath", os.path.join("libs", "MSL"),
            "--reject", "examples",
            "--reject", "baseclasses",
            "--reject", "validation",
            "--lib", os.path.join(repo_path, "Buildings"),
            "--output", output_path]
    r = subprocess.run(args)
    if r.returncode != 0:
        raise ApiGeneratorError(
                "Error generating the api for " + output_path +
                ":\n" + str(r.stdout))

def clone_repo_to(repo, branch, path):
    pwd = os.getcwd()
    os.makedirs(path)
    git_args = ["git", "clone"]
    if QUIET:
        git_args += ["--quiet"]
    git_args += [repo, path]
    val1 = subprocess.run(
            git_args, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    if val1.returncode != 0:
        os.chdir(pwd)        
        raise GitError("Error cloning to " + path + ":\n" + str(val1.stdout))
    os.chdir(path)
    git_args = ["git", "checkout"]
    if QUIET:
        git_args += ["--quiet"]
    git_args += [branch]
    val2 = subprocess.run(
            git_args, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    if val2.returncode != 0:
        os.chdir(pwd)        
        raise GitError(
                "Error checking out " + branch + " for " +
                repo + ":\n" + str(val2.stdout))
    os.chdir(pwd)        

def main(old_gitpath, old_branch, new_gitpath, new_branch, out_path):
    with tempfile.TemporaryDirectory() as dirname:
        # OLD
        old_api = "old_api.json"
        old_repo_path = os.path.join(dirname, "old")
        clone_repo_to(old_gitpath, old_branch, old_repo_path)
        call_api_generator(old_repo_path, "old_api.json")
        # NEW
        new_api = "new_api.json"
        new_repo_path = os.path.join(dirname, "new")
        clone_repo_to(new_gitpath, new_branch, new_repo_path)
        call_api_generator(new_repo_path, "new_api.json")
        # VERSION CHECK
        call_version_checker(old_api, new_api, out_path)

if __name__ == "__main__":
    if len(sys.argv) != 6:
        print("Usage:")
        print(" python main.py {git url for old version} " +
                "{git branch for old version} {git url for new version} " +
                "{git branch for new version} {output file path}")
        sys.exit(0)
    main(sys.argv[1], sys.argv[2], sys.argv[3], sys.argv[4], sys.argv[5])
    print("Done!")
