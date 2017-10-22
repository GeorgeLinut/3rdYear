import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by glinut on 10/15/2017.
 */
public class Main extends Fisiere{
    public static void main(final String[] args) throws IOException {
        int n, m;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        m = Integer.parseInt(br.readLine());

        putInFile(n, m, "matriceA.txt");
        putInFile(m, n, "matriceB.txt");
        int[][] a = new int[n][m];
        int[][] b = new int[m][n];

        readArrayFromFile(n, m, a, "matriceA.txt");
        readArrayFromFile(m, n, b, "matriceB.txt");

        Matrice matrice1 = new Matrice(n,m,a);
        Matrice matrice2 = new Matrice(m,n,b);
        Matrice resultMatrix = Matrice.inmulteste(matrice1, matrice2, 5);

        resultMatrix.printMatrice();
    }
}
