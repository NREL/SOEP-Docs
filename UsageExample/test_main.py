from __future__ import with_statement
from __future__ import absolute_import
import unittest
import main
import os
import subprocess

class TestApp(unittest.TestCase):
    git_repo = u"https://github.com/lbl-srg/modelica-buildings"
    old_branch = u"v3.0.0"
    new_branch = u"v4.0.0"
    output_file = os.path.join(u"temp", u"results.json")

    def setUp(self):
        if not os.path.isdir(u"temp"):
            os.makedirs(u"temp")

    def tearDown(self):
        if os.path.isfile(self.output_file):
            os.remove(self.output_file)

    def test_main(self):
        self.assertTrue(not os.path.isfile(self.output_file))
        main.main(
                self.git_repo,
                self.old_branch,
                self.git_repo,
                self.new_branch,
                self.output_file)
        self.assertTrue(os.path.isfile(self.output_file))

    def test_repository_is_checked_out(self):
        def f(dname):
            path = os.path.join(dname, u"old_test")
            main.clone_repo_to(self.git_repo, self.old_branch, path)
            self.assertTrue(os.path.isdir(path))
            self.assertTrue(len(os.listdir(path)) > 0)
        main.in_temp(f)

    def test_raises_error_on_bad_repo_name(self):
        def f(dname):
            path = os.path.join(dname, u"old_test")
            self.assertRaises(
                    main.GitError,
                    main.clone_repo_to,
                    u"bubba shrimp",
                    self.old_branch,
                    path)
        main.in_temp(f)

    def test_raises_error_on_bad_branch_name(self):
        def f(dname):
            path = os.path.join(dname, u"old_test")
            self.assertRaises(
                    main.GitError,
                    main.clone_repo_to,
                    self.git_repo,
                    u"this-branch-could-not-possibly-exist",
                    path)
        main.in_temp(f)

    def test_run_api_generator_full(self):
        def f(dname):
            path = os.path.join(dname, u"old_test")
            main.clone_repo_to(self.git_repo, self.old_branch, path)
            self.assertTrue(os.path.isdir(os.path.join(path, u"Buildings")))
            output = os.path.join(u"temp", u"old_api.json")
            self.assertTrue(not os.path.isfile(output))
            main.call_api_generator(path, output)
            self.assertTrue(os.path.isfile(output))
            os.remove(output)
        main.in_temp(f)

    def test_run_api_generator(self):
        path = os.path.join(u"libs", u"MBL")
        output = os.path.join(u"temp", u"old_api.json")
        self.assertTrue(not os.path.isfile(output))
        main.call_api_generator(path, output)
        self.assertTrue(os.path.isfile(output))
        os.remove(output)

    def test_run_version_checker(self):
        old_path = os.path.join(u"libs", u"old_api.json")
        new_path = os.path.join(u"libs", u"new_api.json")
        output = os.path.join(u"temp", u"version_diffs.json")
        self.assertTrue(not os.path.isfile(output))
        main.call_version_checker(old_path, new_path, output)
        self.assertTrue(os.path.isfile(output))
        os.remove(output)

    def test_call_version_checker2(self):
        main.call_version_checker(
                os.path.join(u"libs", u"old_api.json"),
                os.path.join(u"libs", u"new_api.json"),
                self.output_file)

    def test_load_pressure_drop(self):
        path = os.path.join(u"libs", u"PressureDrop.mo")
        out_path = os.path.join(u"temp", u"pressure_drop_api.mo")
        self.assertTrue(os.path.isfile(path))
        args = [u"java",
                u"-jar",
                os.path.join(u"libs", u"ModelicaPublicApiGenerator-1.0.0.jar"),
                u"--quiet",
                u"--only", u"PressureDrop",
                u"--modelicapath", os.path.join(u"libs", u"MSL"),
                u"--modelicapath", os.path.join(u"libs", u"MBL", u"Buildings"),
                u"--lib", path,
                u"--output", out_path]
        r = subprocess.call(args)
        if r != 0:
            self.fail(u"Return code was not zero")
        os.remove(out_path)

if __name__ == u"__main__":
    unittest.main()
