import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class OverflowTest {

    public static void main(String... args) throws IOException {
        String dir = args[0];
        File in = Paths.get(dir,"overflow.in").toFile();
        BufferedWriter iw = new BufferedWriter(new FileWriter(in, false));

        iw.write("1000\n");
        for (int i = 0; i < 2000; i++) {
            iw.write(String.valueOf(i + 1));
            iw.write(" ");
        }
        iw.write("\n");
        iw.write("0\n");
        iw.write("0");
        iw.close();

        File out = Paths.get(dir, "overflow.out").toFile();
        BufferedWriter ow = new BufferedWriter(new FileWriter(out, false));
        ow.write("No\n");
        ow.close();
    }

}
