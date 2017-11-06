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
        int nrThreads = Integer.parseInt(br.readLine());
        int nrThreads2 = Integer.parseInt(br.readLine());
        int nrThreads3 = Integer.parseInt(br.readLine());



        putInFile(n, m, "matriceA.txt");
        putInFile(m, n, "matriceB.txt");
        int[][] a = new int[n][m];
        int[][] b = new int[m][n];

        readArrayFromFile(n, m, a, "matriceA.txt");
        readArrayFromFile(m, n, b, "matriceB.txt");

        Matrice matrice1 = new Matrice(n,m,a);
        Matrice matrice2 = new Matrice(m,n,b);
        Matrice resultMatrix = Matrice.inmulteste(matrice1, matrice2, nrThreads);
        Matrice resultM2 = Matrice.inmulteste(matrice1,matrice2,nrThreads2);
        Matrice resultM3 = Matrice.inmulteste(matrice1,matrice2,nrThreads3);

//        resultMatrix.printMatrice();
//        resultM2.printMatrice();
//        resultM3.printMatrice();
    }
}
