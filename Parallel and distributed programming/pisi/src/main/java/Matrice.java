import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by glinut on 10/15/2017.
 */
public class Matrice {
    private int[][] matrice;
    private int linii, coloane;

    public Matrice(int linii, int coloane) {
        this.linii = linii;
        this.coloane = coloane;
        matrice = new int[linii][coloane];
    }

    public Matrice(int linii, int coloane, int[][] matrice) {
        this.linii = linii;
        this.coloane = coloane;
        this.matrice = matrice;
    }

    public int[][] getMatrice() {
        return matrice;
    }

    public void setMatrice(int i, int j, int sum) {
        matrice[i][j] = sum;
    }

    public int getLinii() {
        return linii;
    }

    public void setLinii(int linii) {
        this.linii = linii;
    }

    public int getColoane() {
        return coloane;
    }

    public void setColoane(int coloane) {
        this.coloane = coloane;
    }

    public void printMatrice() {
        for (int i = 0; i < linii; ++i) {
            for (int j = 0; j < coloane; ++j) {
                System.out.print(matrice[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static Matrice aduna(Matrice matrice1, Matrice matrice2, int nrThreads) {
        int operatiiThread = (matrice1.getLinii() * matrice1.getColoane()) / nrThreads;
        int rest = (matrice1.getLinii() * matrice1.getColoane()) % nrThreads;
        Thread[] threads = new Thread[nrThreads + 2];
        Matrice result = new Matrice(matrice1.linii, matrice2.coloane);
        ExecutorService service = Executors.newFixedThreadPool(nrThreads);

        long start = System.currentTimeMillis();

        int iStart = 0, jStart = 0, iStop = 0, jStop = 0;
        for (int i = 1; i <= nrThreads; ++i) {
            int operatiiFinal;
            if (rest > 0) {
                operatiiFinal = operatiiThread + 1;
                rest--;
            } else {
                operatiiFinal = operatiiThread;
            }
            while (operatiiFinal != 0) {
                if (jStop == matrice1.getColoane()) {
                    jStop = 0;
                    ++iStop;
                }
                jStop++;
                operatiiFinal--;
            }
            final int startLinii = iStart, stopLinii = iStop, startColoane = jStart, stopColoane = jStop;
            service.execute(new sumaThread(iStart, jStart, iStop, jStop, matrice1, matrice2, result));
            iStart = iStop;
            jStart = jStop + 1;
            if (jStart == matrice1.coloane) {
                jStart = 0;
                ++iStart;
            }
        }
        long stop = System.currentTimeMillis();
        System.out.println("TIME: " + (stop - start));
        System.out.println(start);
        System.out.println(stop);

        printMatrice(result);
        return result;
    }



    private static void printMatrice(Matrice resultMatrix) {
        for (int i = 0; i < resultMatrix.getLinii(); ++i) {
            for (int j = 0; j < resultMatrix.getColoane(); ++j) {
                System.out.print(resultMatrix.getMatrice()[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static Matrice inmulteste(Matrice matrice1, Matrice matrice2, int nrThreads) {
        int operatiiThread = (matrice1.linii * matrice2.coloane) / nrThreads;
        int rest = (matrice1.linii * matrice2.coloane) % nrThreads;
        Thread[] threads = new Thread[nrThreads + 2];

        Matrice resultMatrix = new Matrice(matrice1.linii, matrice2.coloane);
        ExecutorService service = Executors.newFixedThreadPool(nrThreads);

        long start = System.currentTimeMillis();

        int iStart = 0, jStart = 0, iStop = 0, jStop = 0;
        for (int i = 1; i <= nrThreads; ++i) {
            int operatiiFinal;
            if (rest > 0) {
                operatiiFinal = operatiiThread + 1;
                rest--;
            } else {
                operatiiFinal = operatiiThread;
            }

            while (operatiiFinal != 0) {
                if (jStop == matrice2.getColoane()) {
                    jStop = 0;
                    ++iStop;
                }
                jStop++;
                operatiiFinal--;
            }

            final int  ii = iStart, iii = iStop+1, jj = jStart, jjj = jStop;

            service.execute(new produsThread(matrice1, matrice2, resultMatrix, ii, jj, iii, jjj));
            iStart = iStop;
            jStart = jStop + 1;
            if (jStart == matrice2.coloane) {
                jStart = 0;
                ++ iStart;
            }
        }
        service.shutdown();
        try {
            service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {

        }



        long stop = System.currentTimeMillis();
        System.out.println("TIME: " + (stop - start));
        System.out.println(start);
        System.out.println(stop);
        return resultMatrix;
    }
}


