package ui;

import controller.Controller;
import domain.Matrix;

/**
 * Created by glinut on 11/1/2017.
 */
public class RunSum implements Runnable{
    private int maxNumberOfThreads;
    private Matrix matrixA;
    private Matrix matrixB;
    private Matrix matrixResult;
    private Controller controller;
    private long time;
    private long mostEfficientTime;
    private int mostEfficientNumberOfThreads;

    public RunSum(int maxNumberOfThreads, Matrix matrixA, Matrix matrixB, Matrix matrixResult, Controller controller, long time, long mostEfficientTime, int mostEfficientNumberOfThreads)
    {
        this.maxNumberOfThreads = maxNumberOfThreads;
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.matrixResult = matrixResult;
        this.controller = controller;
        this.time = time;
        this.mostEfficientTime = mostEfficientTime;
        this.mostEfficientNumberOfThreads = mostEfficientNumberOfThreads;
    }

    @Override
    public void run()
    {
        long totalTime = System.currentTimeMillis();
        for (int i = 1; i < maxNumberOfThreads; i++)
        {
            time =  System.currentTimeMillis();
            //System.out.println("Execution with: " + i);

            controller.sum(matrixA, matrixB, matrixResult, i);

            //System.out.println("Time for computation: " + (System.currentTimeMillis() - time) + "millis");

            if(mostEfficientTime > (System.currentTimeMillis() - time))
            {
                mostEfficientTime = (System.currentTimeMillis() - time);
                mostEfficientNumberOfThreads = i;
            }
        }
        System.out.println("Tasks: ");
        System.out.println("Total execution time: " + (System.currentTimeMillis() - totalTime) / 1000);
        System.out.println("Sum statistics: ");
        System.out.println("Fastest time: " + mostEfficientTime + "millis");
        System.out.println("Number of threads: " + mostEfficientNumberOfThreads);
    }
}

