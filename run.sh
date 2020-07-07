#!/bin/bash

for i in "$@"
do
case $i in
	-i=*|--input=*)
	INPUT="${i#*=}"
	shift	
    ;;
    -o=*|--output=*)
	OUTPUT="${i#*=}"
	shift	
    ;;
    -e=*|--executable=*)
	EXECUTABLE="${i#*=}"
	shift	
    ;;
    *)
    ;;
esac
done
java $EXECUTABLE < $INPUT > "program.out"

RES=$(diff program.out $OUTPUT)
status=$?
name=$(cut -d'.' -f 1 <<< $INPUT)
if test $status -eq 0
then
	echo "Test '$name' passed."
else
	echo "Test '$name' failed."
fi
