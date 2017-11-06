/**
 * Created by glinut on 11/4/2017.
 */
public class DoubleProduct implements Runnable {
    protected Matrix matrice1, matrice2, matrice3;
    protected int nrThreads;

    public DoubleProduct(Matrix matrice1, Matrix matrice2, Matrix matrice3,
                         int nrThreads) {
        this.matrice1 = matrice1;
        this.matrice2 = matrice2;
        this.matrice3 = matrice3;
        this.nrThreads = nrThreads;
    }
    @Override
    public void run() {
        this.matrice3  = Matrix.inmulteste(matrice1,matrice2,nrThreads);
    }
}
