import controller.Controller;
import domain.Matrix;
import ui.RunMultiply;
import ui.RunSum;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by glinut on 11/1/2017.
 */
public class Main
{
    public static void main(String[] args)
    {
        int rows    = 1000;
        int columns = 1000;
        long time = 9000;
        long mostEfficientTime = 9000;
        int mostEfficientNumberOfThreads = 900000000;

        ExecutorService executorService = Executors.newFixedThreadPool(9);

        Matrix matrixA = new Matrix(rows, columns);
        Matrix matrixB = new Matrix(rows, columns);
        Matrix matrixResult = new Matrix(rows, columns);

        //the number of threads is the product of the lines and columns
        int maxNumberOfThreads = 10;
        Controller controller = new Controller();

        //generate matrix with random numbers
        matrixA.createMatrix();
        matrixB.createMatrix();

        //run both methods in parallel
        RunSum runSum           = new RunSum(maxNumberOfThreads, matrixA, matrixB, matrixResult, controller, time, mostEfficientTime, mostEfficientNumberOfThreads);
        RunMultiply runMultiply = new RunMultiply(maxNumberOfThreads, matrixA, matrixB, matrixResult, controller, time, mostEfficientTime, mostEfficientNumberOfThreads);
        System.out.println("Both methods running in the same time:");
        System.out.println("\n\n");


        executorService.submit(runSum);
        executorService.submit(runMultiply);
        executorService.shutdownNow();


    }
}