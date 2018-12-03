#!/bin/bash
# Program takes one integer argument: the day of the week [1, 5]
day="$1"

scriptroot=$(dirname "$(readlink -f $0)")
cd $scriptroot/..
qiesroot=$PWD
mts=$qiesroot/IntegrationWorkspace/transactionSummary.txt
if [[ -f $mts ]]; then
    rm $mts
fi

# Compile front office if it does not already exist
frontname=frontend.jar
frontexe=${qiesroot}/bin/${frontname}
if ! [[ -f $frontexe ]]; then  
	cd $qiesroot/front_src
	$(javac -d ${qiesroot}/bin Interface.java Parser.java Database.java)
	cd $qiesroot/bin 
	$(jar cfe ${frontname} Interface *)
	rm *.class
fi

# Compile back office if it does not already exist
backname=backend.jar
backexe=${qiesroot}/bin/${backname}
if ! [[ -f $backexe ]]; then
	cd $qiesroot/back_src
	$(javac -d ${qiesroot}/bin BackParser.java BackDatabase.java Service.java)
	cd $qiesroot/bin
	$(jar cfe ${backname} BackParser BackParser.class BackDatabase.class Service.class)
	rm *.class
fi

# For each console
for d in $qiesroot/IntegrationWorkspace/console*; do
    # run front end with input in ./input
    cd $d
    java -jar $frontexe < ./inputs/day${day} > "/dev/null"
    cat "transactionSummary.txt" >> $mts
done

echo " - MTS - "
cat $mts

# Run back office
cd $qiesroot/IntegrationWorkspace/

if ! [[ -f centralServices.txt ]]; then
    touch centralServices.txt
fi

java -jar ${backexe} > "/dev/null"

echo " - CSF - "
cat centralServices.txt
echo " - VSF - "
cat validServices.txt

echo ""
