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
