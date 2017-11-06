import java.util.Random;

/**
 * Created by glinut on 11/6/2017.
 */
public class Matrix {
    private int nRows;
    private int nColumns;
    private int[][] values;

    public Matrix(int nRows, int nColumns) {
        this.nRows = nRows;
        this.nColumns = nColumns;
        values = new int[nRows][nColumns];
        Random randomGen = new Random();
        for (int i = 0; i < nRows; ++i) {
            for (int j = 0; j < nColumns; ++j) {
                values[i][j] = randomGen.nextInt() % 100;
            }
        }
    }

    public Matrix(int nRows, int nColumns, int[][] values) {
        this.nRows = nRows;
        this.nColumns = nColumns;
        this.values = values;
    }

    public int[] getRow(int i) {
        return values[i];
    }

    public int getnRows() {
        return nRows;
    }

    public int getnColumns() {
        return nColumns;
    }

    public int getValue(int i, int j) {
        return values[i][j];
    }

    public int[][] getValues() {
        return values;
    }

    public void setValues(int[][] values) {
        this.values = values;
    }

    public void setValue(int i, int j, int value) {
        values[i][j] = value;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < nRows; ++i) {
            for (int j = 0; j < nColumns; ++j) {
                s.append(values[i][j]);
                s.append(" ");
            }
            s.append("\n");
        }

        return s.toString();
    }
}

