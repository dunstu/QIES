#!/bin/bash
cd ./src

$(javac -d ../bin Interface.java Parser.java Database.java)

cd ../bin

$(jar cfe frontendtest.jar Interface *)

cd ../

java -jar bin/frontendtest.jar

cd ./"Tests"

file=0

for d in */Input.txt ;

do
  if [ -f "$d" ] ; then
    file=$((file+1))
	echo $d
  fi
done




echo "files $file"