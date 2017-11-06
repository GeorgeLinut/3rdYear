/**
 * Created by glinut on 11/6/2017.
 */
public class MatrixLine {
    private int start, end;
    private int[][] values;

    public MatrixLine(int start, int end, int[][] values) {
        this.start = start;
        this.end = end;
        this.values = values;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int[][] getValues() {
        return values;
    }
}

