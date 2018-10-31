#!/bin/bash

cd Testing/Output_Tests

for d in ./*/; do 
    cd "$d"
    touch .index
    cd ..
done
