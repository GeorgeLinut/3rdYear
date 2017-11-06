/**
 * Created by glinut on 11/4/2017.
 */
public class TripleProduct  implements Runnable{

    protected Matrix matrice1, matrice2, matrice3;
    protected int nrThreads;

    public TripleProduct(Matrix matrice1, Matrix matrice2, Matrix matrice3,
                         int nrThreads) {
        this.matrice1 = matrice1;
        this.matrice2 = matrice2;
        this.matrice3 = matrice3;
        this.nrThreads = nrThreads;
    }
    @Override
    public void run() {
        try {
            this.matrice3  = Matrix.inmulteste3(matrice1,matrice2,nrThreads);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
