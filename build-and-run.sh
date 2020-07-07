#!/bin/bash

./generate-tests.sh

javac Solution.java

files=`ls tests/*.in`
for input in $files
do
	name=$(cut -d'.' -f 1 <<< $input)
	output="${name}.out"

	./run.sh -e=Solution -i=$input -o=$output
done
