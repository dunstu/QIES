#!/bin/bash

exe=backendTest.jar

# Compile the program into a .jar
cd ./back_src
$(javac -d ../bin BackParser.java BackDatabase.java Service.java)
cd ../bin
$(jar cfe ${exe} BackParser BackParser.class BackDatabase.class Service.class)
exepath=$PWD/${exe}

cd ../BackTesting/CreateService/
root=$PWD
testSummaryFile=${root}/testSummary.txt
echo "" > $testSummaryFile

# For each test case
for d in Input_Tests/*/ ;
do
    testname=${d#*/}
    cd ./Input_Tests/$testname
    expectedcsf="$root/Expected_Tests/${testname}centralServices.txt"
    expectedvsf="$root/Expected_Tests/${testname}validServices.txt"
    outputcsf="$root/Output_Tests/${testname}centralServices.txt"
    outputvsf="$root/Output_Tests/${testname}validServices.txt"

    # Clear the output directory of old output
    cat "centralServices.txt" > $outputcsf
    echo "" > $outputvsf    

    # Run the program
    java -jar ${exepath} > "/dev/null" 2>&1

    # If the program exited correctly (output exists), record output files
    if [ -f "newCentralServices.txt" ]; then
        cat "newCentralServices.txt" > $outputcsf
        rm "newCentralServices.txt"
    fi
    if [ -f "validServices.txt" ]; then
        cat "validServices.txt" > $outputvsf
        rm "validServices.txt"
    fi

    # See if the output differs from what was expected
    diff "$expectedcsf" "$outputcsf" > "/dev/null"
    diffReturnCSF=$?
    diff "$expectedvsf" "$outputvsf" > "/dev/null"
    diffReturnVSF=$? 
    
    # Record whether the output differs or not
    if (( $diffReturnCSF == 0 && $diffReturnVSF == 0 )); then
        echo "Pass : $testname" >> $testSummaryFile
    else
        echo "Fail : $testname" >> $testSummaryFile
    fi

    cd ../../
 
done

cat testSummary.txt

