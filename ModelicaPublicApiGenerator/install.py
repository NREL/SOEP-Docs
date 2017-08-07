# Copyright 2017 Big Ladder Software LLC, All rights reserved.
# 
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions are met:
# 
# (1) Redistributions of source code must retain the above copyright notice,
# this list of conditions and the following disclaimer.
# 
# (2) Redistributions in binary form must reproduce the above copyright notice,
# this list of conditions and the following disclaimer in the documentation
# and/or other materials provided with the distribution.
# 
# (3) Neither the name of the copyright holder nor the names of its
# contributors may be used to endorse or promote products derived from this
# software without specific prior written permission. 
# 
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
# AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
# IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
# ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER, THE UNITED STATES
# GOVERNMENT, OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
# SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
# PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
# OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
# WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
# OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
# ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
#
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
