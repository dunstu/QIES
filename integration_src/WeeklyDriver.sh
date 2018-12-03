#!/bin/bash

scriptroot=$(dirname "$(readlink -f "$0")")
cd $scriptroot/..
qiesroot=$PWD

if [[ -f $qiesroot/IntegrationWorkspace/centralServices.txt ]]; then
	rm $qiesroot/IntegrationWorkspace/centralServices.txt
fi
if [[ -f $qiesroot/IntegrationWorkspace/validServices.txt ]]; then
	echo "00000" > $qiesroot/IntegrationWorkspace/validServices.txt	
fi

echo "Compiling Front Office..."
# Compile front office
frontname=frontend.jar
frontexe=${qiesroot}/bin/${frontname}
cd $qiesroot/front_src
$(javac -d ${qiesroot}/bin Interface.java Parser.java Database.java)
cd $qiesroot/bin 
$(jar cfe ${frontname} Interface *)
rm *.class
echo "done."

echo "Compiling Back Office..."
# Compile back office
backname=backend.jar
backexe=${qiesroot}/bin/${backname}
cd $qiesroot/back_src
$(javac -d ${qiesroot}/bin BackParser.java BackDatabase.java Service.java)
cd $qiesroot/bin
$(jar cfe ${backname} BackParser BackParser.class BackDatabase.class Service.class)
rm *.class
echo "done."

cd $qiesroot/IntegrationWorkspace

for i in {1..5}; do
	echo ----------- Day $i ------------
	$qiesroot/integration_src/DailyDriver.sh $i
done

