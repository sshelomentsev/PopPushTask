import java.io.PrintStream;
import java.util.Scanner;

/**
 * Railroads solution
 */
public class OptimizedSolution {

    private static final String POSITIVE_ANSWER = "Yes";
    private static final String NEGATIVE_ANSWER = "No";

    private static final int END_MARKER = 0;

    public static void main(String... args) {
        PrintStream out = System.out;
        Scanner sc = new Scanner(System.in);

        boolean isBlock = false;
        TrainState state = new TrainState();

        while (sc.hasNextInt()) {
            int n = sc.nextInt();

            if (END_MARKER == n) {
                if (isBlock) { // end of block
                    isBlock = false;
                } else { // end of file
                    return;
                }
            } else if (isBlock) {
                int res = state.push(n);
                if (res <= 0) {
                    state.clear();
                    out.println(0 == res ? POSITIVE_ANSWER : NEGATIVE_ANSWER);
                }
                if (-1 == res) {
                    sc.nextLine();
                }
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

        private int size = 0;
        private int expectedSize = 0; // number of expected values to add to the train
        private int maxPossibleStackValue = 0; // max possible stack value at the moment
        private int currentMaxValue = 0; // max value of a coach already added to the line B

        /**
         * Pushes coach to the train.
         *
         * @param coachNumber
         * @return number of expected values to add if this push is correct, -1 otherwise
         */
        int push(int coachNumber) {
            if (coachNumber > currentMaxValue) { // some pushes to stack and one pop
                maxPossibleStackValue = coachNumber - 1;
                currentMaxValue = coachNumber;
            } else { // only pop
                // at some previous iteration we added a value which couldn't be at the top of the stack that iteration.
                // so, now we've got incorrect state because current value is greater than max expected and
                // should have been added earlier
                // e.g.
                // let's B = [1 5 4 2 3]
                // part [2 3] is not correct because 3 can't come stack earlier than 2
                // at the time 4 was processed, expectedSize = 2, max possible stack value is 3, and current max is 5
                // at the next iteration (v=2), max possible stack value is 1 and expected size is 1
                // but at the final iteration value is 3 which don't match to the order constraint
                if (maxPossibleStackValue < coachNumber) {
                    return -1;
                }

                maxPossibleStackValue = coachNumber - 1;
            }

            return --expectedSize;
        }

        void setSize(int size) {
            this.size = size;
            clear();
        }

        int getSize() {
            return size;
        }

        void clear() {
            maxPossibleStackValue = 0;
            currentMaxValue = 0;
            expectedSize = size;
        }

    }

}
