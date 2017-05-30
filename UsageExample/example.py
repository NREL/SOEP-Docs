
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
