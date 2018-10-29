#!/bin/bash
cd ./src

$(javac -d ../bin Interface.java Parser.java Database.java)

cd ../bin

$(jar cfe frontendtest.jar Interface *)

cd ../

java -jar bin/frontendtest.jar
