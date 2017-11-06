import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by glinut on 10/15/2017.
 */
public class Matrix {
    private int[][] matrice;
    ArrayList<Lock> locks = new ArrayList<>();
    ArrayList<Condition> ready = new ArrayList<>();
    private int linii, coloane;

    public Matrix(int linii, int coloane) {
        this.linii = linii;
        this.coloane = coloane;
        matrice = new int[linii][coloane];
        for (int i=0;i<coloane;i++){
            locks.add(new ReentrantLock());
        }
        for (int i=0;i<coloane;i++){
            ready.add(locks.get(i).newCondition());
        }

    }

    public Matrix(int linii, int coloane, int[][] matrice) {
        this.linii = linii;
        this.coloane = coloane;
        this.matrice = matrice;
        for (int i=0;i<coloane;i++){
            locks.add(new ReentrantLock());
        }
        for (int i=0;i<coloane;i++){
            ready.add(locks.get(i).newCondition());
        }
    }

    public int[][] getMatrix() {
        return matrice;
    }

    public void setMatrix(int i, int j, int sum) {
        matrice[i][j] = sum;
    }

    public int getValue(int i,int j){
        return matrice[i][j];
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

    public void printMatrix() {
        for (int i = 0; i < linii; ++i) {
            for (int j = 0; j < coloane; ++j) {
                System.out.print(matrice[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static Matrix aduna(Matrix matrice1, Matrix matrice2, int nrThreads) {
        int operatiiThread = (matrice1.getLinii() * matrice1.getColoane()) / nrThreads;
        int rest = (matrice1.getLinii() * matrice1.getColoane()) % nrThreads;
        Thread[] threads = new Thread[nrThreads + 2];
        Matrix result = new Matrix(matrice1.linii, matrice2.coloane);
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
            service.execute(new SumThread(iStart, jStart, iStop, jStop, matrice1, matrice2, result));
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

        printMatrix(result);
        return result;
    }



    private static void printMatrix(Matrix resultMatrix) {
        for (int i = 0; i < resultMatrix.getLinii(); ++i) {
            for (int j = 0; j < resultMatrix.getColoane(); ++j) {
                System.out.print(resultMatrix.getMatrix()[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static Matrix inmulteste(Matrix matrice1, Matrix matrice2, int nrThreads) {
        int operatiiThread = (matrice1.linii * matrice2.coloane) / nrThreads;
        int rest = (matrice1.linii * matrice2.coloane) % nrThreads;
        Thread[] threads = new Thread[nrThreads + 2];

        Matrix resultMatrix = new Matrix(matrice1.linii, matrice2.coloane);
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
            service.execute(new ProductThread(matrice1, matrice2, resultMatrix, ii, jj, iii, jjj));
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

    public static Matrix inmulteste3(Matrix matrice1, Matrix matrice2, int nrThreads) throws InterruptedException {
        int[][] result = new int[matrice1.linii][matrice2.coloane];
        long start = System.currentTimeMillis();

        int done = 0;
        while (done<matrice1.linii){
            for (int line=0;line<matrice1.linii;line++){
                Thread.sleep(1000);
                    matrice1.locks.get(line).lock();
                    try {
                        matrice1.ready.get(line).await();
                        int [][] ints = new int[1][matrice2.linii];
                        for (int j=0;j<matrice2.linii;j++){
                            ints[0][j]=matrice1.matrice[line][j];
                        }
                        Matrix intern = new Matrix(1,matrice1.coloane,ints);
                        Matrix internRes = Matrix.inmulteste(intern,matrice2,nrThreads/matrice1.linii);
                        done++;
                        for (int i=0;i<matrice2.coloane;i++){
                            result [line][i] = internRes.matrice[0][i];
                        }
                    }
                    finally {
                        matrice1.locks.get(line).unlock();
                    }

                }
            }
//bettylorincz@gmail.com


        long stop = System.currentTimeMillis();
        System.out.println("TIME: " + (stop - start));
        System.out.println(start);
        System.out.println(stop);
        Matrix resultMatrix = new Matrix(matrice1.linii, matrice2.coloane,result);

        return resultMatrix;
    }

//    public void pump(int i) {
//        this.ready.set(i,this.ready.get(i)+1);
//    }
}


