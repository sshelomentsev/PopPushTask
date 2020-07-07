# PopPushTask

## Prerequisites
* OS GNU/Linux, OS/X, or Windows with Cygwin (or any other bash shell installed)
* OpenJDK 7+

## How to run
* You can execute a script `build-and-run-suite.sh` to generate tests files, build a program, and run it on the test suite. Test files will be created in the tests/ directory. The script prints a result of test runs.
* If you want to run only a particular test, you execute a script `build-and-run.sh` with mandatory arguments `-i=[path to input file]` and `-o=[path to output file]`.


## Description of files in the repository
* Solution.java -- the source code of PopPush task solution.
* TestWriter.java -- the source code of generator of test cases for different sizes of trains.
* TestOverflow.java -- the source code of stack overflow tests generator.
* generate-tests.sh -- the script to create input and output files of test cases.
* build-and-run-suite.sh -- the script which call test generator, build a program, and run it on available test suite.
* build-and-run.sh -- the script to build a program, and run it on given test case.
