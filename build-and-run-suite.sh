#!/bin/bash

./generate-tests.sh

javac OptimizedSolution.java

files=`ls tests/*.in`
for input in $files
do
	name=$(cut -d'.' -f 1 <<< $input)
	output="${name}.out"

	./run.sh -e=OptimizedSolution -i=$input -o=$output
done
