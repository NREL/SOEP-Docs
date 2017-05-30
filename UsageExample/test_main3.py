import unittest
import main3
import tempfile
import os
import subprocess

class TestApp(unittest.TestCase):
    git_repo = "https://github.com/lbl-srg/modelica-buildings"
    old_branch = "v3.0.0"
    new_branch = "v4.0.0"
    output_file = os.path.join("temp", "results.json")

    def setUp(self):
        if not os.path.isdir("temp"):
            os.makedirs("temp")

    def tearDown(self):
        if os.path.isfile(self.output_file):
            os.remove(self.output_file)

    def test_main(self):
        self.assertTrue(not os.path.isfile(self.output_file))
        main3.main(
                self.git_repo,
                self.old_branch,
                self.git_repo,
                self.new_branch,
                self.output_file)
        self.assertTrue(os.path.isfile(self.output_file))

    def test_repository_is_checked_out(self):
        with tempfile.TemporaryDirectory() as dname:
            path = os.path.join(dname, "old_test")
            main3.clone_repo_to(self.git_repo, self.old_branch, path)
            self.assertTrue(os.path.isdir(path))
            self.assertTrue(len(os.listdir(path)) > 0)

    def test_raises_error_on_bad_repo_name(self):
        with tempfile.TemporaryDirectory() as dname:
            path = os.path.join(dname, "old_test")
            self.assertRaises(
                    main3.GitError,
                    main3.clone_repo_to,
                    "bubba shrimp",
                    self.old_branch,
                    path)

    def test_raises_error_on_bad_branch_name(self):
        with tempfile.TemporaryDirectory() as dname:
            path = os.path.join(dname, "old_test")
            self.assertRaises(
                    main3.GitError,
                    main3.clone_repo_to,
                    self.git_repo,
                    "this-branch-could-not-possibly-exist",
                    path)

    def test_run_api_generator_full(self):
        with tempfile.TemporaryDirectory() as dname:
            path = os.path.join(dname, "old_test")
            main3.clone_repo_to(self.git_repo, self.old_branch, path)
            self.assertTrue(os.path.isdir(os.path.join(path, "Buildings")))
            output = os.path.join("temp", "old_api.json")
            self.assertTrue(not os.path.isfile(output))
            main3.call_api_generator(path, output)
            self.assertTrue(os.path.isfile(output))
            os.remove(output)

    def test_run_api_generator(self):
        path = os.path.join("libs", "MBL")
        output = os.path.join("temp", "old_api.json")
        self.assertTrue(not os.path.isfile(output))
        main3.call_api_generator(path, output)
        self.assertTrue(os.path.isfile(output))
        os.remove(output)

    def test_run_version_checker(self):
        old_path = os.path.join("libs", "old_api.json")
        new_path = os.path.join("libs", "new_api.json")
        output = os.path.join("temp", "version_diffs.json")
        self.assertTrue(not os.path.isfile(output))
        main3.call_version_checker(old_path, new_path, output)
        self.assertTrue(os.path.isfile(output))
        os.remove(output)

    def test_call_version_checker2(self):
        main3.call_version_checker(
                os.path.join("libs", "old_api.json"),
                os.path.join("libs", "new_api.json"),
                self.output_file)

    def test_load_pressure_drop(self):
        path = os.path.join("libs", "PressureDrop.mo")
        out_path = os.path.join("temp", "pressure_drop_api.mo")
        self.assertTrue(os.path.isfile(path))
        args = ["java",
                "-jar",
                os.path.join("libs", "ModelicaPublicApiGenerator-1.0.0.jar"),
                "--quiet",
                "--only", "PressureDrop",
                "--modelicapath", os.path.join("libs", "MSL"),
                "--modelicapath", os.path.join("libs", "MBL", "Buildings"),
                "--lib", path,
                "--output", out_path]
        r = subprocess.run(args)
        if r.returncode != 0:
            self.fail("Return code was not zero")
        os.remove(out_path)

if __name__ == "__main__":
    unittest.main()
