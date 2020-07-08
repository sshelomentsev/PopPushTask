import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * Railroads solution
 */
public class Solution {

    private static final String POSITIVE_ANSWER = "Yes";
    private static final String NEGATIVE_ANSWER = "No";

    private static final String END_MARKER = "0";

    public static void main(String... args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintStream out = System.out;

        boolean isBlock = false;
        int trainSize = 0;

        while (reader.ready()) {
            String line = reader.readLine();
            boolean isEnd = END_MARKER.equals(line.trim());

            if (isEnd) {
                isBlock = false;
            } else {
                if (isBlock) {
                    // of course, there is can be parsing numbers directly from file without creating
                    // an array of int, without additional costs on memory allocation/gc,
                    // but no time execution limit was specified, so I prefer to add a separate method
                    // with an array as an argument.
                    boolean result = verifyPermutation(trainSize, getPermutation(line));
                    out.println(result ? POSITIVE_ANSWER : NEGATIVE_ANSWER);
                } else {
                    if (0 != trainSize) {
                        out.println();
                    }
                    trainSize = Integer.parseInt(line.trim());
                    if (0 == trainSize) {
                        return;
                    }
                    isBlock = true;
                }
            }
        }
    }

    private static int[] getPermutation(String raw) {
        String[] parts = raw.split("\\s+");
        int[] p = new int[parts.length];
        for (int i = 0; i < p.length; i++) {
            p[i] = Integer.parseInt(parts[i]);
        }
        return p;
    }

    /**
     * Verifies if given coaches permutation can be done by train reorganization.
     * @param size defined size of train
     * @param permutation array of coaches numbers
     * @return true is fiven permutation is possible, false otherwise
     */
    private static boolean verifyPermutation(int size, int[] permutation) {
        // We can imagine that line A is a queue (def Q1) filled some positive integers in natural order.
        // Q has only poll() and peek() methods available.
        // Line B is a queue with only add() method available.
        // Also, we can imagine that the station is a stack (def S) with pop(), push(), and peek() methods available.
        // So, we can define reorganization process as a list of instructions:
        // 1: moveS -- move from A to the station or Q1.poll() and S.push()
        // 2: moveB -- move from the station to B or S.pop() and Q2.add()
        // To verify that the given permutation is possible, we can try to make a reverse process.
        // That is try to get the sequence in natural order by polling values from Q2 to S and adding values from S to Q1.
        // At each iteration we have a number we are wait for move to the Q1.
        // If at some moment we can't get the required value from the top of S and Q1 is empty,
        // this state is not correct, so the permutation is not valid.

        if (size != permutation.length) { // guess input data should be correct, but let it be checked
            return false;
        }

        Stack station = new Stack(size); // station
        int counter = size; // sequence number of a coach we are wait to board to the line A at the current state

        int i = permutation.length - 1; // index of the coach we can currently move from line B to the station

        // checking if the reverse coaches permutation can be performed
        while (counter > 0) {
            int v = station.peek();
            if (v == counter) { // top element of the stack is equal to the coach number we are wait to move
                // move the coach from the station to line A
                station.pop();
                counter--;
                // check station again on the next iteration
                continue;
            } else if (i == -1) {
                // line B is empty, but station still has some at least one coach we can currently move to the line A
                // this permutation is not valid
                return false;
            }
            // we can't move any coach from the station
            // look for coaches in line B
            if (i >= 0) {
                // move coach from line B to the station
                int coach = permutation[i--];
                station.push(coach);
            }
        }

        return true;
    }

    /**
     * The stack class represents a LIFO stack of positive integers with fixes size.
     * When stack is created, it contains no items.
     */
    private static class Stack {

        public static final int EMPTY_VALUE = 0;

        private final int size;
        private final int[] items;
        private int top;

        /**
         * Creates an empty stack.
         * @param size stack size.
         */
        Stack(int size) {
            this.size = size;
            this.items = new int[size];
            this.top = -1;
        }

        /**
         * Pushes an item onto the top of this stack.
         * @param item the item to be pushed.
         * @return true if it's possible to push the value,
         *         false if stack size exceeded.
         */
        boolean push(int item) {
            if (top >= (size - 1)) { // stack overflow
                return false;
            }
            items[++top] = item;
            return true;
        }

        /**
         * Removes the item at the top and returns that item.
         * @return The value at the top if this stack is not empty, 0 otherwise.
         */
        int pop() {
            if (top < 0) { // stack is empty
                return EMPTY_VALUE;
            }
            top--;
            return items[top + 1];
        }

        /**
         * Looks at the object at the top of this stack without removing it from the stack.
         * @return The value at the top if this stack is not empty, 0 otherwise.
         */
        int peek() {
            return top > -1 ? items[top] : EMPTY_VALUE;
        }

    }

}
