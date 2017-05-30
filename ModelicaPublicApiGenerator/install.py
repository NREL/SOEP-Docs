#!/usr/env python
# This file should be run once to create a Maven local repository
# and add dependencies to it.
# This script should run with either the Python 2.7.13 or Python 3.6.1 or there
# abouts. It has been explicitly tested for 2.7.13 and 3.6.1
from __future__ import print_function
import subprocess
import os

def make_mvn_install_cmd(jar_path, gID, aID, ver):
    if not os.path.exists(jar_path):
        raise Exception("Path doesn't exist: {0}".format(jar_path))
    return ["mvn",
            "install:install-file",
            "-Dfile={0}".format(jar_path),
            "-DgroupId={0}".format(gID),
            "-DartifactId={0}".format(aID),
            "-Dversion={0}".format(ver),
            "-Dpackaging=jar",
            "-DlocalRepositoryPath=maven_repository"]

def call(*args):
    try:
        subprocess.call(*args)
    except:
        subprocess.run(*args)

jars = [{"path": "lib/beaver-rt-0.9.6.1.jar",
         "grID": "net.sf.beaver",
         "arID": "beaver-rt",
         "ver": "0.9.6.1"},
        {"path": "lib/modelica-compiler-2.0.jar",
         "grID": "org.jmodelica",
         "arID": "modelica-compiler",
         "ver": "2.0"},
        {"path": "lib/util-2.0.jar",
         "grID": "org.jmodelica",
         "arID": "util",
         "ver": "2.0"}]

for j in jars:
    print("Installing {0}".format(j["path"]))
    call(make_mvn_install_cmd(j["path"], j["grID"], j["arID"], j["ver"]))
print("Done!")
