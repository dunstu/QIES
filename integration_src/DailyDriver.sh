#!/bin/bash
# Program takes one integer argument: the day of the week [1, 5]
day="$1"

# Record where the file is being run in the filesystem
scriptroot=$(dirname "$(readlink -f $0)")
cd $scriptroot/..
qiesroot=$PWD

# Record the location of the Merged Transaction Summary File
mts=$qiesroot/IntegrationWorkspace/transactionSummary.txt
if [[ -f $mts ]]; then  # Clean the directory of an old mts
    rm $mts
fi
# Record the location of the Valid Services File
vsf=$qiesroot/IntegrationWorkspace/validServices.txt
if ! [[ -f $vsf ]]; then  # If a valid services file doesnt exist, create an empty one
    echo "00000" > $vsf
fi
# Record the location of the Central Services File
csf=$qiesroot/IntegrationWorkspace/centralServices.txt
if ! [[ -f $csf ]]; then  # If a central services file does not exist, create an empty one
    echo "" > $csf
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

# Display the Merged Transaction Summary File
echo " - MTS - "
cat $mts

# Run Back Office
cd $qiesroot/IntegrationWorkspace/
java -jar ${backexe} > "/dev/null"

# Display the Central Services and Valid Services Files
echo " - CSF - "
cat $csf
echo " - VSF - "
cat $vsf
echo ""

