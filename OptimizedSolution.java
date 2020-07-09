import java.io.PrintStream;
import java.util.Scanner;

/**
 * Railroads solution
 */
public class OptimizedSolution {

    private static final String POSITIVE_ANSWER = "Yes";
    private static final String NEGATIVE_ANSWER = "No";

    private static final int END_MARKER = 0;

    private static final int MAX_SIZE = 1000;

    public static void main(String... args) {
        PrintStream out = System.out;
        Scanner sc = new Scanner(System.in);

        boolean isBlock = false;
        TrainState state = new TrainState(MAX_SIZE);

        while (sc.hasNextInt()) {
            int n = sc.nextInt();

            if (END_MARKER == n) {
                if (isBlock) { // end of block
                    isBlock = false;
                } else { // end of file
                    return;
                }
            } else if (isBlock) {
                state.clear();
                state.pushItem(n);
                for (int i = 1; i < state.getSize(); i++) {
                    int s = sc.nextInt();
                    state.pushItem(s);
                }
                out.println(state.isValid() ? POSITIVE_ANSWER : NEGATIVE_ANSWER);
            } else {
                isBlock = true;
                if (0 != state.getSize()) {
                    out.println();
                }
                state.setSize(n);
            }


        }
    }

    /**
     * Class represents the current state of train reorganization
     */
    private static class TrainState {

        private static final int EMPTY_VALUE = 0;

        private int index;
        private int size;
        private int top;

        private int[] p;
        private int[] items;

        TrainState(int maxSize) {
            this.p = new int[maxSize];
            this.items = new int[maxSize];
        }

        /**
         * Pushes coach to the train.
         *
         * @param item coach number
         */
        void pushItem(int item) {
            p[index++] = item;
        }

        boolean isValid() {
            top = -1;
            int counter = size; // sequence number of a coach we are wait to board to the line A at the current state

            int i = size - 1; // index of the coach we can currently move from line B to the station

            // checking if the reverse coaches permutation can be performed
            while (counter > 0) {
                int v = peek();
                if (v == counter) { // top element of the stack is equal to the coach number we are wait to move
                    // move the coach from the station to line A
                    pop();
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
                    int coach = p[i--];
                    push(coach);
                }
            }

            return true;
        }

        private boolean push(int item) {
            if (top >= (size - 1)) { // stack overflow
                return false;
            }
            items[++top] = item;
            return true;
        }

        private int pop() {
            if (top < 0) { // stack is empty
                return EMPTY_VALUE;
            }
            top--;
            return items[top + 1];
        }

        private int peek() {
            return top > -1 ? items[top] : EMPTY_VALUE;
        }

        void setSize(int size) {
            this.size = size;
            clear();
        }

        int getSize() {
            return size;
        }

        void clear() {
            index = 0;
        }

    }

}
