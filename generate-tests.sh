#!/bin/bash

TEST_DIR=tests
mkdir -p $TEST_DIR

cd $TEST_DIR

echo "0" > empty.in
echo -n > empty.out

tee single.in > /dev/null <<EOT
1
1
0
0
EOT

tee single.out > /dev/null <<EOT
Yes
EOT

tee two.in > /dev/null <<EOT
2
1 2
2 1
0
0
EOT

tee two.out > /dev/null <<EOT
Yes
Yes
EOT

tee invalid_input.in > /dev/null <<EOT
4
1 2
0
1
2 1
0
5
1 2 3 4 4
1 2 3 4 9
0
0
EOT

tee invalid_input.out > /dev/null <<EOT
No

No

No
No
EOT

cd ..

javac TestWriter.java
capacities=(10 11 50 100 300 500 1000)

for item in "${capacities[@]}";
do
	java TestWriter $TEST_DIR $item
done

javac OverflowTest.java
java OverflowTest $TEST_DIR

