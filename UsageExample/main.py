
# Module for Python 2.7
from __future__ import with_statement
from __future__ import absolute_import
import os
import subprocess
import tempfile
import sys
import shutil

QUIET = False

class VersionCheckerError(Exception):
    def __init__(self, message=u""):
        self.message = message
    def __str__(self):
        return u"VersionCheckerError: " + self.message

class ApiGeneratorError(Exception):
    def __init__(self, message=u""):
        self.message = message
    def __str__(self):
        return u"ApiGeneratorError: " + self.message

class GitError(Exception):
    def __init__(self, message=u""):
        self.message = message
    def __str__(self):
        return u"GitError: " + self.message

def call_version_checker(old_path, new_path, output):
    args = [u"java",
            u"-jar",
            os.path.join(u"libs", u"VersionChecker-1.0.0.jar"),
            u"--output", output,
            old_path,
            new_path]
    r = subprocess.call(args)
    if r != 0:
        raise VersionCheckerError(u"Error calling VersionChecker")

def call_api_generator(repo_path, output_path):
    args = [u"java",
            u"-jar",
            os.path.join(u"libs", u"ModelicaPublicApiGenerator-1.0.0.jar"),
            u"--quiet",
            u"--modelicapath", os.path.join(u"libs", u"MSL"),
            u"--reject", u"examples",
            u"--reject", u"baseclasses",
            u"--reject", u"validation",
            u"--lib", os.path.join(repo_path, u"Buildings"),
            u"--output", output_path]
    r = subprocess.call(args)
    if r != 0:
        raise ApiGeneratorError(
                u"Error generating the api for " + output_path)

def clone_repo_to(repo, branch, path):
    pwd = os.getcwdu()
    os.makedirs(path)
    git_args = [u"git", u"clone"]
    if QUIET:
        git_args += [u"--quiet"]
    git_args += [repo, path]
    val1 = subprocess.call(
            git_args, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    if val1 != 0:
        os.chdir(pwd)        
        raise GitError(u"Error cloning to " + path)
    os.chdir(path)
    git_args = [u"git", u"checkout"]
    if QUIET:
        git_args += [u"--quiet"]
    git_args += [branch]
    val2 = subprocess.call(
            git_args, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    if val2 != 0:
        os.chdir(pwd)        
        raise GitError(
                u"Error checking out " + branch + u" for " + repo)
    os.chdir(pwd)        

def in_temp(f):
    path = tempfile.mkdtemp()
    f(path)
    shutil.rmtree(path)

def main(old_gitpath, old_branch, new_gitpath, new_branch, out_path):
    def f(dirname):
        # OLD
        old_api = u"old_api.json"
        old_repo_path = os.path.join(dirname, u"old")
        clone_repo_to(old_gitpath, old_branch, old_repo_path)
        call_api_generator(old_repo_path, u"old_api.json")
        # NEW
        new_api = u"new_api.json"
        new_repo_path = os.path.join(dirname, u"new")
        clone_repo_to(new_gitpath, new_branch, new_repo_path)
        call_api_generator(new_repo_path, u"new_api.json")
        # VERSION CHECK
        call_version_checker(old_api, new_api, out_path)
    in_temp(f)

if __name__ == u"__main__":
    if len(sys.argv) != 6:
        print u"Usage:"
        print (u" python main.py {git url for old version} " +
                u"{git branch for old version} {git url for new version} " +
                u"{git branch for new version} {output file path}")
        sys.exit(0)
    main(sys.argv[1], sys.argv[2], sys.argv[3], sys.argv[4], sys.argv[5])
    print u"Done!"
