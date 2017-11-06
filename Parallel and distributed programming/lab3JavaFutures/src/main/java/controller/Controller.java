package controller;

import domain.Matrix;
import threadOperations.ThreadAddMatrix;
import threadOperations.ThreadMultiplyMatrix;

import java.util.concurrent.*;

/**
 * Created by glinut on 11/1/2017.
 */
public class Controller {
    /**
     *
     * @param matrixA -
     * @param matrixB -
     * @param matrixResult -
     * @param numberOfThreads -
     */
    public void sum(Matrix matrixA, Matrix matrixB, Matrix matrixResult, int numberOfThreads)
    {
        //create new executor by the number of threads
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(numberOfThreads);

        //for every index create new Thread - if the max number of threads allow us
        for (Integer i = 0; i < matrixA.getLines(); i++)
        {
            for (Integer j = 0; j < matrixA.getColumns(); j++)
            {
                final int ii = i;
                final int jj = j;

                Runnable task = () -> new ThreadAddMatrix(matrixA, matrixB, matrixResult, ii, jj);
                executor.scheduleAtFixedRate(task, 0, 1, TimeUnit.SECONDS);
                //executor.submit(new Thread(new ThreadMatrixAdd(matrixA, matrixB, matrixResult, i, j)));
            }
        }
        executor.shutdown();
    }

    /**
     *
     * @param matrixA -
     * @param matrixB -
     * @param matrixResult -
     * @param numberOfThreads -
     */
    // TODO: 10/30/2017 multiply is computed using futures
    public void multiply(Matrix matrixA, Matrix matrixB, Matrix matrixResult, int numberOfThreads)
    {
        //create new executor by the number of threads
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numberOfThreads);

        //for every index create new Thread - if the max number of threads allow us
        for (Integer i = 0; i < matrixA.getLines(); i++)
            for (Integer j = 0; j < matrixA.getColumns(); j++)
            {
                Future<?> future = executor.submit(new Thread(new ThreadMultiplyMatrix(matrixA, matrixB, matrixResult, i, j)));
                try
                {
                    future.get();
                }
                catch (InterruptedException | ExecutionException ignored)
                {

                }
            }
        executor.shutdown();
    }
}
