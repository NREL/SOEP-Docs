# Example Usage: Python, Git, VersionChecker, and ModelicaPublicApiGenerator

This project is a simple Python script to demonstrate how one might use the
ModelicaPublicApiGenerator and VersionChecker together with the git distributed
version control system.

## Setup

Prerequisites:

- Python (tested with 2.7.13 and 3.6.1)
- Git (tested with 2.13.0)
- Java (tested with 1.8.0\_131)

## Usage

The files without the trailing "3" are for Python version 2.7.13.

    > python main.py {git url for old version} {git branch for old version} {git url for new version} {git branch for new version} {output file path}

The files with a trailing "3" have been tested with Python 3.6.1.

    > python main3.py {git url for old version} {git branch for old version} {git url for new version} {git branch for new version} {output file path}

The script is a minimal demonstration of how to use the
`ModelicaPublicApiGenerator` and `VersionChecker` Java JAR packages together
with `Git` (and `Python`). A script such as this one could be configured on a
CI system to detect backward incompatible changes in Modelica library files.

## License

Copyright 2017 Big Ladder Software LLC, All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

(1) Redistributions of source code must retain the above copyright notice,
this list of conditions and the following disclaimer.

(2) Redistributions in binary form must reproduce the above copyright notice,
this list of conditions and the following disclaimer in the documentation
and/or other materials provided with the distribution.

(3) Neither the name of the copyright holder nor the names of its
contributors may be used to endorse or promote products derived from this
software without specific prior written permission. 

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER, THE UNITED STATES
GOVERNMENT, OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
