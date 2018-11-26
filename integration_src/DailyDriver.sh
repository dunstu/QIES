#!/bin/bash
# Program takes one integer argument: the day of the week [1, 5]
day="$1"

scriptroot=$(dirname "$(readlink -f "$0")")
cd $scriptroot/..
qiesroot=$PWD
mts=$qiesroot/IntegrationWorkspace/transactionSummary.txt
if [[ -f $mts ]]; then
    rm $mts
fi

# Compile front office
frontname=frontend.jar
cd $qiesroot/front_src
$(javac -d ../bin Interface.java Parser.java Database.java)
cd $qiesroot/bin 
$(jar cfe ${frontname} Interface *)
rm *.class
frontexe=${qiesroot}/bin/${frontname}

# Compile back office
backname=backend.jar
cd $qiesroot/back_src
$(javac -d ../bin BackParser.java BackDatabase.java Service.java)
cd $qiesroot/bin
$(jar cfe ${backname} BackParser BackParser.class BackDatabase.class Service.class)
rm *.class
backexe=${qiesroot}/bin/${backname}

# For each console
for d in $qiesroot/IntegrationWorkspace/console*; do
    # run front end with input in ./input
    cd $d
    java -jar $frontexe < ./inputs/day${day} > "/dev/null"
    cat "transactionSummary.txt" >> $mts
done

# Run back office    

    
