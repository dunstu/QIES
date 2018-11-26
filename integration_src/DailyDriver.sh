#!/bin/bash

scriptroot=$(dirname "$(readlink -f "$0")")
cd $scriptroot/..
qiesroot=$PWD

# Compile front office
frontexe=frontend.jar
cd $qiesroot/front_src
$(javac -d ../bin Interface.java Parser.java Database.java)
cd $qiesroot/bin 
$(jar cfe ${frontexe} Interface *)
rm *.class
cd $qiesroot

# Compile back office
backexe=backend.jar
cd $qiesroot/back_src
$(javac -d ../bin BackParser.java BackDatabase.java Service.java)
cd $qiesroot/bin
$(jar cfe ${backexe} BackParser BackParser.class BackDatabase.class Service.class)
rm *.class
cd $qiesroot


# For each console

    # run front end with input in ./input

    # Append output to ../mergedTransactionSummary 

# Run back office    

    
