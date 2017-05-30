# MBL Parser Tool

This project provides a means for parsing out the public API for any Modelica
Model and writing it to a JSON file.

## Building

Prior to beginning build, please be sure that you have the following installed:

- Maven
- Java Development Kit 1.8 or later

To build, dependencies must first be downloaded for JModelica since the jar
files are not available on package repositories such as Maven Central. 

The easiest way to do this is to download and install JModelica 2.0 and
grab the jar files from:

    C:\JModelica.org-2.0\install\ThirdParty\Beaver\lib\beaver-rt.jar
    => lib/beaver-rt-0.9.6.1.jar
    C:\JModelica.org-2.0\install\lib\ModelicaCompiler.jar
    => lib/modelica-compiler-2.0.jar
    C:\JModelica.org-2.0\install\lib\util.jar
    => lib/util-2.0.jar

All other dependencies can be automatically installed.

In order to house the above dependencies together with their versioning
numbering and such, it is best to create a local maven repository. This
can be done by running the python program, `install.py` *after* you've
downloaded and renamed the above libraries:

    python install.py

This should work with either python 2.7+ or 3.5+ and will create the
local folder `maven_repository` which contains the proper build artifacts
as Maven likes them.

To build the project, then just type:

    mvn clean package

The target jar will be:

    target/mbl-parser-{VERSION}-jar-with-dependencies.jar

### Adding files for the test suite.

Tests are run against the Modelica Standard Library (MSL) and the Modelica
Buildings Library (MBL). To facilitate this, please place the MSL and MBL in
folders named "MSL" and "MBL" in the top directory of this project. They will
be ignored by git but will be used to run the test suite. Since the tests must
be run to package, it is recommended to place them in this location.

We are currently using MSL Version 3.2.1 (Build 4)

And for BSL, we are using: Buildings Library 4.0.0 (2017-03-29)

## Usage

Usage information can be obtained by passing the `--help` flag:

    Modelica Public API Generator
    VERSION: 1.0.0

    Given modelica paths and a library root, create the public facing API for the
    that library. For example,

       java -jar ModelicaPublicApiGenerator-1.0.0.jar -mp MSL -L MBL

    Usage: <main class> [options]
      Options:
        --accept, -A
          Accept only dotted paths containing the given text
        --help, -h
          Show usage and help message
        --lib, -L
          Name of a library to parse in its entirety
        --modelicapath, -mp
          Modelica Path: multiple entries are OK
          Default: []
        --only, -1
          Process only single files (more than one can be specified)
        --output, -o
          Output file name to write; defaults to 'out.json'
          Default: out.json
        --quiet, -q
          Suppress command line printing
          Default: false
        --reject, -R
          Reject dotted paths containing the given text
        --version, -v
          Show version information
          Default: false
           java -jar target/ModelicaPublicApiGenerator-1.0.0-jar-with-dependencies.jar {flags and arguments go here}

The tool will generate a JSON file consisting of a large map from string of
dotted path name to class public API.

### Processing the API for a Library

The below example uses the filtering system (see discussion later).

       java -jar target/ModelicaPublicApiGenerator-1.0.0-jar-with-dependencies.jar \
            --modelicapath {path to Modelica Standard Library} \
            --reject examples \
            --reject baseclasses \
            --reject validation \
            --lib {path to modelica buildings library/"Buildings"} \
            --output {output file path}

### Processing a Single File

A single file can be processed as follows:

       java -jar target/ModelicaPublicApiGenerator-1.0.0-jar-with-dependencies.jar \
          --only {dotted-path} -mp {path to modelica standard library} \
          -mp {path to any other library} \
          --lib {path to the single file *.mo} \
          -o {output file path}

Above, the dotted path is the path of the instance of interest from the single
file.

### Filtering

Filtering can be accomplished by specifying 1 or more `--accept` or `--reject` flags. Specifying any `--accept`
flags will cause the flitering system to reject all qualified names (i.e., dotted paths) except for the ones where
the word specified by `--accept <word to accept>` is contained in the path.

Similarly, specifying a `--reject {word to reject}` flag will cause all paths to be accepted *except* for paths
that contain the word to reject. If a path is rejected, no subpaths will be investigated (because they will also
contain the same word match).

When both are specified, the acceptance filter runs first, then the rejection filter.

## Dependencies

Please see the `pom.xml` file for a detailed list of dependencies. For
convenience, those dependencies and their licenses are summarized here:

- com.google.code.gson/gson 2.8.0: Apache License 2.0
  [link](https://github.com/google/gson/blob/master/LICENSE)
- net.sf.beaver/beaver-rt 0.9.6.1: BSD License
  [link](https://sourceforge.net/projects/beaver/)
- org.jmodelica/modelica-compiler 2.0: GPL v3
  [link](http://www.jmodelica.org/page/24)
- org.jmodelica/util 2.0: GPL v3
  [link](http://www.jmodelica.org/page/24)
- junit/junit 4.12: Eclipse Public License 1.0
  [link](http://junit.org/junit4/license.html)
- com.beust/jcommander 1.69: Apache License 2.0
  [link](http://jcommander.org/#_license)
- org.apache.commons/commons-lang3 3.5: Apache License 2.0
  [link](https://git-wip-us.apache.org/repos/asf?p=commons-lang.git;a=blob_plain;f=LICENSE.txt;hb=577f7b3b5462fdf84c0956a3bec5296bf7821677)
