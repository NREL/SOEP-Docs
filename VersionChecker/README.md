# VersionChecker

This tool is designed to consume a JSON API file of the format:

    {"<fully-qualified class name>": { ... <class public API> ...}}

and write out a JSON file with the list of all backward incompatible changes:

    ["<backward incompatible change #1>", "<change number 2>", etc.]

# Significant changes:

The following listing of significant changes are detected by this library:

- removing a default value from a public parameter
- changing the name or path of or removing a (public)
  - model
  - connector
  - parameter
  - variable
- adding new parameters without defaults

# Building

We use [Apache Maven](https://maven.apache.org/) to build:

    mvn clean package

Note: there are also files present to work with IntelliJ although any IDE is
fine.

# Running

An example commandline invocation would be:

    java -jar target/VersionChecker-0.0.1-SNAPSHOT-jar-with-dependencies.jar \
      -o junk.json src/test/resources/a_v1.json src/test/resources/a_v2.json

# Dependencies

Please see `pom.xml` file for details. However, the dependencies and links to
their license information is summarized below for convenience:

- com.google.code.gson/gson 2.8.0: Apache License 2.0
  [link](https://github.com/google/gson/blob/master/LICENSE)
- junit/junit 4.12: Eclipse Public License 1.0
  [link](http://junit.org/junit4/license.html)
- com.beust/jcommander 1.69: Apache License 2.0
  [link](http://jcommander.org/#_license)
- org.apache.commons/commons-lang3 3.5: Apache License 2.0
  [link](https://git-wip-us.apache.org/repos/asf?p=commons-lang.git;a=blob_plain;f=LICENSE.txt;hb=577f7b3b5462fdf84c0956a3bec5296bf7821677)

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
PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


