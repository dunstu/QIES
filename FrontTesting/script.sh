#!/bin/bash
for d in */Input.txt; do
    echo "exit" >> "${d}"
done
