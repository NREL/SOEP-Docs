# Package Overview

This package contains three software projects:

1. `ModelicaPublicApiGenerator`

    This project is a Java-based commandline application to generate the public
    API of Modelica libraries (or single files) in JSON format.

2. `VersionChecker`

    This project is a Java-based commandline application to check for backward
    incompatible changes between two public API files as generated from the
    tool mentioned in 1, above. The idea is that a public API can be generated
    for two different versions of a library and the `VersionChecker` tool can
    be used to list all backward incompatible changes.

3. `UsageExample`

    This project shows how to use the above two commandline tools along with
    the git distributed version control system to create APIs for differing
    versions of a repository and test them for backward incompatible changes.

Each package contains its own README file with detailed instructions on how
to build.

One additional file in this directory is the `package.sh` shell script
(usable on Mac OS X or Unix) that automates the building and copying of
files from the `ModelicaPublicApiGenerator` and `VersionChecker` projects
and placement into the `UsageExample` project.

Detailed information on 3rd party code dependencies can be found in each
library.

# License

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
PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
