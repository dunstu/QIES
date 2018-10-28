#!/bin/bash
cd src
$(javac Interface.java )
echo "login
planner
createservice
22222
19980214
Duncan
logout" | $(java -cp . Interface)
exit

