#I'm a bad person. I wrote it in snobol, http://www.snobol4.org

import os
import sys

out = sys.argv[1]
os.system("./SNOBOLBESTLANGUAGE/snobol4 ./SNOBOLBESTLANGUAGE/json_from_uiautomator.sno <" + out + ">./IO/new_json.txt")