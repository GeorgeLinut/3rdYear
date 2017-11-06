import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by glinut on 11/7/2017.
 */
public class ProducerConsumer {
    private final Queue<MatrixLine> items;
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private Matrix a,b,c;
    private int[][]result;
    private int nThProd;
    private ExecutorService poolProd,poolCons;
    private int counter = -1;

    public ProducerConsumer(int m, int n, int p, int q, int nThreadsProducer, int nThreadsConsumer) {
        items = new LinkedList<>();
        a = new Matrix(m, n);
        b = new Matrix(n, p);
        c = new Matrix(p, q);
        nThProd = nThreadsProducer;
        poolProd = Executors.newFixedThreadPool(nThreadsProducer);
        poolCons = Executors.newFixedThreadPool(nThreadsConsumer);
        result = new int[m][q];
    }

    public void push(MatrixLine data) throws InterruptedException{
        lock.lock();
        try {
            System.out.println("Producer adds data: lines " + data.getStart() + "-" + data.getEnd());
            items.add(data);
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public void consumer(){
        while(true) {
            if(counter == 0) {
                break;
            }
            lock.lock();
            MatrixLine res;
            try {
                while (items.isEmpty()) {
                    try {
                        notEmpty.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                res = items.remove();
                System.out.println("Consumer removes data lines " + res.getStart() + "-" + res.getEnd());

            } finally {
                lock.unlock();
            }
            multiplyConsumer(res);
            counter--;
        }
    }

    public void multiplyProducer() {
        List<Future<MatrixLine>> futures = new ArrayList<>();

        int slice = a.getnRows() / nThProd;
        if (slice < 1) {
            slice = 1;
        }
        for (int i = 0; i < a.getnRows(); i += slice) {
            Callable<MatrixLine> task = new CallableLineMultiplier(a.getValues(), b, i, i + slice);
            Future<MatrixLine> submit = poolProd.submit(task);
            futures.add(submit);
        }
        if(a.getnRows() % nThProd != 0) {
            Callable<MatrixLine> task = new CallableLineMultiplier(a.getValues(), b, slice*nThProd, a.getnRows());
            Future<MatrixLine> submit = poolProd.submit(task);
            futures.add(submit);
        }
        counter = futures.size();
        MatrixLine futureResult;
        for (Future<MatrixLine> f : futures) {
            try {
                futureResult = f.get();
                this.push(futureResult);

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public void multiplyConsumer(MatrixLine lines) {
        int start = lines.getStart(), end = lines.getEnd();
        int[][] res;
        Future<MatrixLine> future;
        Callable<MatrixLine> task = new CallableLineMultiplier(lines.getValues(), c, 0, end-start);
        future = poolCons.submit(task);
        try {
            MatrixLine r = future.get();
            res = r.getValues();
            System.arraycopy(res, 0, result, start, end-start);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

    }

    public void run() throws InterruptedException {
        Thread t1 = new Thread(this::multiplyProducer);
        Thread t2 = new Thread(this::consumer);
        t2.start();
        t1.start();
        t1.join();
        t2.join();
        poolCons.shutdown();
        poolProd.shutdown();
        printResult();
        integrityCheck();
    }

    private void printResult() {
        for (int i=0;i<c.getnRows();i++){
            for (int j=0;j<c.getnColumns();j++){
                System.out.print(c.getValue(i,j)+" ");
            }
            System.out.println();
        }
    }

    private void integrityCheck() {
        int m = a.getnRows(), n = a.getnColumns(), p = b.getnColumns(), q = c.getnColumns(), v;
        int[][] partial = new int[m][p];
        for(int i = 0; i < m; ++i) {
            for(int j = 0; j < p; ++j) {
                partial[i][j] = 0;
                for(int k = 0; k < n; ++k) {
                    partial[i][j] += a.getValue(i, k) * b.getValue(k, j);
                }
            }
        }
        for(int i = 0; i < m; ++i) {
            for(int j = 0; j < q; ++j) {
                v = 0;
                for(int k = 0; k < p; ++k) {
                    v += partial[i][k] * c.getValue(k, j);
                }
                if(v != result[i][j]) {
                    throw new RuntimeException("Inconsistency! :( " + i + " " + j);
                }
            }
        }
    }

}
