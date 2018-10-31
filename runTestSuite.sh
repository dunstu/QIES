#!/bin/bash

exe=frontendtest.jar

cd ./src

$(javac -d ../bin Interface.java Parser.java Database.java)

cd ../bin

$(jar cfe ${exe} Interface *)

cd ../

#java -jar bin/frontendtest.jar

cd ./Testing


for d in Input_Tests/*/ ;
do
    testname=${d#*/}
    input="./Input_Tests/$testname/Input.txt"
    expected="./Expected_Tests/$testname/Output.txt"
    output="./Output_Tests/$testname/Output.txt"
    echo "" > testSummary.txt

    echo $testname

    java -jar ../bin/"$exe" < "$input" > "templog.txt"
    cat "transactionSummary.txt" > "$output"
    diff "$expected" "$output" > "/dev/null"

    if (( $? == 0 )); then
        echo "Pass : $testname" >> testSummary.txt
    else
        echo "Fail : $testname" >> testSummary.txt
    fi
 
done
