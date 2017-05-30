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
