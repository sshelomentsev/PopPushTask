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

tee without_stack_cases.in > /dev/null <<EOT
9
3 6 9 8 7 5 4 2 1
3 6 9 8 7 4 5 2 1
0
12
3 6 9 8 7 5 4 2 1 10 11 12
3 6 9 8 7 5 4 2 1 10 12 11
3 6 9 8 7 5 4 2 1 12 10 11
3 6 9 8 7 5 4 2 1 12 11 10
0
0
EOT

tee without_stack_cases.out > /dev/null <<EOT
Yes
No

Yes
Yes
No
Yes
EOT

cd ..

javac TestWriter.java
capacities=(10 11 50 100 300 500 1000)

for item in "${capacities[@]}";
do
	java TestWriter $TEST_DIR $item
done


