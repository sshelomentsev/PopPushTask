import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class TestWriter {

    private static final String POSITIVE_ANS = "Yes";
    private static final String NEGATIVE_ANS = "No";

    public static void main(String... args) throws IOException {
        String dir = args[0];
        String name = args[1];
        int n = Integer.parseInt(name);

        File in = Paths.get(dir, name + ".in").toFile();
        BufferedWriter iw = new BufferedWriter(new FileWriter(in, false));

        File out = Paths.get(dir, name + ".out").toFile();
        BufferedWriter ow = new BufferedWriter(new FileWriter(out, false));

        iw.write(String.valueOf(n));
        iw.write("\n");

        int[] a = new int[n];

        fillNaturalOrder(a);
        printArray(iw, a);
        printAnswer(ow, POSITIVE_ANS);

        fillReversedNaturalOrder(a);
        printArray(iw, a);
        printAnswer(ow, POSITIVE_ANS);

        fillRestInIncorrectOrder(a);
        printArray(iw, a);
        printAnswer(ow, NEGATIVE_ANS);

        bufferOddValuesInCorrectOrder(a);
        printArray(iw, a);
        printAnswer(ow, POSITIVE_ANS);

        bufferOddValuesInNaturalOrder(a);
        printArray(iw, a);
        printAnswer(ow, NEGATIVE_ANS);

        severalPushOnePop(a);
        printArray(iw, a);
        printAnswer(ow, POSITIVE_ANS);

        iw.write("0\n");
        iw.write("0");

        iw.close();
        ow.close();
    }

    private static void printAnswer(BufferedWriter w, String answer) throws IOException {
        w.write(answer + "\n");
    }

    private static void printArray(BufferedWriter w, int[] a) throws IOException {
        for (int value : a) {
            w.write(String.valueOf(value));
            w.write(" ");
        }
        w.newLine();
    }
    // 1 2 3 4 5
    private static void fillNaturalOrder(int[] a) {
        for (int i = 0; i < a.length; i++) {
            a[i] = i + 1;
        }
    }

    // 5 4 3 2 1
    private static void fillReversedNaturalOrder(int[] a) {
        for (int i = 0; i < a.length; i++) {
            a[i] = a.length - i;
        }
    }

    // 5 4 3 1 2
    private static void fillRestInIncorrectOrder(int[] a) {
        int n = a.length;
        int half = n / 2;
        for (int i = 0; i < half; i++) {
            a[i] = n - i;
        }
        for (int i = half; i < n; i++) {
            a[i] = i - half + 1;
        }
    }

    // 2 4 5 3 1
    private static void bufferOddValuesInCorrectOrder(int[] a) {
        int n = a.length;
        int half = n / 2;
        int shift = n % 2 == 0 ? -1 : 0;
        for (int i = 0; i < half; i++) {
            a[i] = 2 * (i + 1);
            a[i + half] = n - 2 * i + shift;
        }
        if (n % 2 == 1) {
            a[n - 1] = 1;
        }
    }

    // 2 4 1 3 5
    private static void bufferOddValuesInNaturalOrder(int[] a) {
        int n = a.length;
        int half = n / 2;
        for (int i = 0; i < half; i++) {
            a[i] = 2 * (i + 1);
            a[i + half] = 2 * i + 1;
        }
        if (n % 2 == 1) {
            a[n - 1] = n;
        }
    }

    // 3 6 9 8 7 5 4 2 1
    private static void severalPushOnePop(int[] a) {
        int n = a.length;
        int c = 3;
        int k = n / c;
        for (int i = 0; i < k; i++) {
            a[i] = (i + 1) * c;
        }
        int s = n;
        for (int i = k; i < n; i++) {
            if (s % c == 0) {
                s--;
            }
            a[i] = s;
            s--;
        }
    }

}
