#!/bin/bash

# Record where the script is being run from in the filesystem
scriptroot=$(dirname "$(readlink -f "$0")")
cd $scriptroot/..
qiesroot=$PWD

# Compile front office
echo "Compiling Front Office..."

frontname=frontend.jar
frontexe=${qiesroot}/bin/${frontname}
cd $qiesroot/front_src
$(javac -d ${qiesroot}/bin Interface.java Parser.java Database.java)
cd $qiesroot/bin 
$(jar cfe ${frontname} Interface *)
rm *.class

echo "done."

# Compile back office
echo "Compiling Back Office..."

backname=backend.jar
backexe=${qiesroot}/bin/${backname}
cd $qiesroot/back_src
$(javac -d ${qiesroot}/bin BackParser.java BackDatabase.java Service.java)
cd $qiesroot/bin
$(jar cfe ${backname} BackParser BackParser.class BackDatabase.class Service.class)
rm *.class

echo "done."

# Run the system for 5 days
cd $qiesroot/IntegrationWorkspace
for i in {1..5}; do
	echo ----------- Day $i ------------
	$qiesroot/integration_src/DailyDriver.sh $i
done

