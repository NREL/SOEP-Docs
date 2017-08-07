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

import json
import main

REPO_URL = "https://github.com/lbl-srg/modelica-buildings"
NEW_BRANCH = "master"
OLD_BRANCH = "v4.0.0"
OLD_API_FILE = "api-" + OLD_BRANCH + ".json"
NEW_API_FILE = "api-" + NEW_BRANCH + ".json"
DIFFS_FILE = "diffs-" + NEW_BRANCH + "-vs-" + OLD_BRANCH + ".json"

main.clone_repo_to(REPO_URL, OLD_BRANCH, OLD_BRANCH)
main.clone_repo_to(REPO_URL, NEW_BRANCH, NEW_BRANCH)
main.call_api_generator(OLD_BRANCH, OLD_API_FILE)
main.call_api_generator(NEW_BRANCH, NEW_API_FILE)
main.call_version_checker(OLD_API_FILE, NEW_API_FILE, DIFFS_FILE)

with open(OLD_API_FILE) as fid:
    old_data = fid.read()
with open(NEW_API_FILE) as fid:
    new_data = fid.read()
with open(DIFFS_FILE) as fid:
    diff_data = fid.read()

old_api = json.loads(old_data)
new_api = json.loads(new_data)
diffs = json.loads(diff_data)
