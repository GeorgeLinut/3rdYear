import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by glinut on 11/4/2017.
 */
public class FileWork {
    static Random rand = new Random();

    public static void readArrayFromFile(int n, int m, int[][] a, String fileName) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(fileName));
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                a[i][j] = scanner.nextInt();
                System.out.print(a[i][j] + " ");
            }
            System.out.println();

        }
        System.out.println();

    }

    public static void putInFile(int n, int m, String fileName) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter writer = new PrintWriter(fileName, "UTF-8");
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                writer.print(rand.nextInt(10));
                writer.print(" ");
            }
            writer.println();
        }

        writer.close();
    }
}
