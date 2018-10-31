#!/bin/bash

exe=frontendtest.jar

cd ./src

$(javac -d ../bin Interface.java Parser.java Database.java)

cd ../bin

$(jar cfe ${exe} Interface *)

cd ../

#java -jar bin/frontendtest.jar

cd ./Testing/Input_tests


#for d in Input_Tests/*/ ;
#do
#testname=${d#*/}
testname="Cancelticket - correct service number as agent"
input="./Input_Tests/$testname/Input.txt"
expected="./Expected_Tests/$testname/Output.txt"
output="./Output_Tests/$testname/Output.txt"
echo "" > testSummary.txt

echo 1
java -jar ../bin/"$exe" < "$input" > "templog.txt"
echo 2
cat "transactionSummary.txt" > "$output"
echo 3
diff "$expected" "$output" > "/dev/null"

if (( $? == 0 )); then
    echo "Pass : $testname" >> testSummary.txt
else
    echo "Fail : $testname" >> testSummary.txt
fi
 
#done
