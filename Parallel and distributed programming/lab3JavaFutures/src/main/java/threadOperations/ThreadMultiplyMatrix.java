package threadOperations;

import domain.Matrix;

/**
 * Created by glinut on 11/1/2017.
 */
public class ThreadMultiplyMatrix implements Runnable
{
    private Matrix matrixA;
    private Matrix matrixB;
    private Matrix matrixResult;
    private Integer row;
    private Integer column;

    public ThreadMultiplyMatrix(Matrix matrixA, Matrix matrixB, Matrix matrixResult, Integer row, Integer column)
    {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.matrixResult = matrixResult;
        this.row = row;
        this.column = column;
    }

    @Override
    public void run()
    {
        for (int k = 0; k < matrixB.getColumns(); k++)
            matrixResult.setValue(row, column, matrixA.get(row, column) + matrixB.get(row, column));
    }
}
