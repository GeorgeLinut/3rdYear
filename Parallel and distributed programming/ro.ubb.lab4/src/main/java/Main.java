import javax.xml.ws.soap.MTOM;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by glinut on 11/4/2017.
 */
public class Main extends FileWork{
    public static void main(final String[] args) throws IOException, InterruptedException {
        int n, m;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        m = Integer.parseInt(br.readLine());
        //int nrThreads = Integer.parseInt(br.readLine());
//        int nrThreads2 = Integer.parseInt(br.readLine());
//        int nrThreads3 = Integer.parseInt(br.readLine());


        putInFile(n, m, "matriceA.txt");
        putInFile(m, n, "matriceB.txt");
        putInFile(n,n,"matriceC.txt");
        int[][] a = new int[n][m];
        int[][] b = new int[m][n];
        int[][] c = new int[n][n];

        readArrayFromFile(n, m, a, "matriceA.txt");
        readArrayFromFile(m, n, b, "matriceB.txt");
        readArrayFromFile(n,n,c,"matriceC.txt");

        Matrix matrice1 = new Matrix(n,m,a);
        Matrix matrice2 = new Matrix(m,n,b);
        Matrix matrice3 = new Matrix(n,n,c);
        Matrix resultMatrix = new Matrix(n,n);
        Matrix finalMatrix = new Matrix(n,n);
        ExecutorService service = Executors.newFixedThreadPool(2);
        service.execute(new DoubleProduct(matrice1, matrice2,resultMatrix,n));
        service.execute(new TripleProduct(resultMatrix, matrice3, finalMatrix,n));
        service.shutdown();
        service.awaitTermination(10L, TimeUnit.SECONDS);

        resultMatrix.printMatrix();
        finalMatrix.printMatrix();
    }
}
