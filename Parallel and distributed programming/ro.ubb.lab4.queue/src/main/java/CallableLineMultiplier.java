import java.util.concurrent.Callable;

/**
 * Created by glinut on 11/6/2017.
 */
public class CallableLineMultiplier implements Callable<MatrixLine> {
    private int[][] A;
    private Matrix B;
    private int start;
    private int end;

    public CallableLineMultiplier(int[][] a, Matrix b, int start, int end) {
        this.A = a;
        this.B = b;
        this.start = start;
        this.end = end;
    }

    @Override
    public MatrixLine call() {
        int[][] result = new int[A.length][B.getnColumns()];
        for (int i = start; i < end; ++i) {
            for (int j = 0; j < B.getnColumns(); ++j) {
                for (int k = 0; k < B.getnRows(); ++k) {
                    result[i - start][j] += A[i][k] * B.getValue(k, j);
                }
            }
        }
        return new MatrixLine(start, end, result);
    }
}
