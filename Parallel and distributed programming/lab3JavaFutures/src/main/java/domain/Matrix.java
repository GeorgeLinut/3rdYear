package domain;

import java.util.Random;

/**
 * Created by glinut on 11/1/2017.
 */

public class Matrix
{
    private int lines;
    private int columns;
    private int[][] matrix;
    private Random random = new Random();

    public Matrix(int lines, int columns)
    {
        this.lines = lines;
        this.columns = columns;
        this.matrix = new int[lines][columns];
    }

    public void createMatrix()
    {
        for (int i = 0; i < lines; i++)
            for (int j = 0; j < columns; j++)
                matrix[i][j] = random.nextInt(100);
    }

    public int[][] getMatrix()
    {
        return matrix;
    }

    public int getLines()
    {
        return lines;
    }

    public void setLines(int lines)
    {
        this.lines = lines;
    }

    public int getColumns()
    {
        return columns;
    }

    public void setColumns(int columns)
    {
        this.columns = columns;
    }

    public void print()
    {
        for (int i = 0; i < getLines(); i++)
        {
            for (int j = 0; j < getColumns(); j++)
                System.out.print(matrix[i][j] + " ");
            System.out.println();
        }
    }

    public void setValue(int row, int column, int value)
    {
        matrix[row][column] = value;
    }

    public int get(Integer row, Integer column)
    {
        return matrix[row][column];
    }
}

