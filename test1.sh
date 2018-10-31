#!/bin/bash

#mkdir ../testoutputs
cd ./src

$(javac -d ../bin Interface.java Parser.java Database.java)

cd ../bin

$(jar cfe test1.jar Interface *)

cd ../

#java -jar bin/test1.jar

cd ./"Testing/Input_tests"

file=0

for d in */Input.txt ;
do
  if [ -f "$d" ] ; then
    file=$((file+1))
    
	  cat */Input.txt 
   
  fi
done


echo "files $file"