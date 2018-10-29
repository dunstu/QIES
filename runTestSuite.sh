#!/bin/bash

exe=frontendtest.jar

cd ./src

$(javac -d ../bin Interface.java Parser.java Database.java)

cd ../bin

$(jar cfe ${exe} Interface *)

cd ../

#java -jar bin/frontendtest.jar

cd ./"Testing"

file=0

for d in Input_Tests/*/ ;
do
  testname=${d#*/}
  cat "Input_Tests/${testname}/Input.txt" | $(java -jar ../bin/$exe) > "Output_Tests/${testname}/Output.txt"
done




echo "files $file"
