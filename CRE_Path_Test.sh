#!/bin/bash

exe=backendTest.jar

cd ./back_src

$(javac -d ../bin BackParser.java BackDatabase.java Service.java)

cd ../bin

$(jar cfe ${exe} BackParser BackParser.class BackDatabase.class Service.class)
#rm *.class

exepath=$PWD/${exe}

#java -jar bin/frontendtest.jar

cd ../BackTesting/CreateService/

root=$PWD
testSummaryFile=${root}/testSummary.txt

echo "" > $testSummaryFile

for d in Input_Tests/*/ ;
do
    testname=${d#*/}
    cd ./Input_Tests/$testname
    expectedcsf="$root/Expected_Tests/${testname}centralServices.txt"
    expectedvsf="$root/Expected_Tests/${testname}validServices.txt"
    outputcsf="$root/Output_Tests/${testname}centralServices.txt"
    outputvsf="$root/Output_Tests/${testname}validServices.txt"

    java -jar ${exepath} > "/dev/null"
    cat "newCentralServices.txt" > $outputcsf
    cat "validServices.txt" > $outputvsf
    rm "newCentralServices.txt"
    rm "validServices.txt"

    diff "$expectedcsf" "$outputcsf" > "/dev/null"
    diffReturnCSF=$?
    diff "$expectedvsf" "$outputvsf" > "/dev/null"
    diffReturnVSF=$? 
    
    if (( $diffReturnCSF == 0 && $diffReturnVSF == 0 )); then
        echo "Pass : $testname" >> $testSummaryFile
    else
        echo "Fail : $testname" >> $testSummaryFile
    fi

    cd ../../
 
done

cat testSummary.txt

