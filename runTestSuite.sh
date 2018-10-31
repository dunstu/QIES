#!/bin/bash

exe=frontendtest.jar

cd ./src

$(javac -d ../bin Interface.java Parser.java Database.java)

cd ../bin

$(jar cfe ${exe} Interface *)

cd ../

#java -jar bin/frontendtest.jar

cd ./Testing/Input_tests


