/**
 * Created by glinut on 11/4/2017.
 */
public class SumThread implements Runnable {
    protected int startLinii, startColoane, stopLinii, stopColoane;
    protected Matrix m1, m2, rez;

    public SumThread(int startLinii, int startColoane, int stopLinii, int stopColoane, Matrix m1, Matrix m2, Matrix rez) {
        this.startLinii = startLinii;
        this.startColoane = startColoane;
        this.stopLinii = stopLinii;
        this.stopColoane = stopColoane;
        this.m1 = m1;
        this.m2 = m2;
        this.rez = rez;
    }

    @Override
    public void run() {

        for (int i = startLinii; i <= stopLinii; i++){
            for (int j = 0; j < m2.getColoane(); j++){
                if (i == startLinii){
                    if (j >= startColoane)
                        rez.setMatrix(i,j,m1.getMatrix()[i][j] + m2.getMatrix()[i][j]);
                } else if (i == stopLinii) {
                    if (j <= stopColoane)
                        rez.setMatrix(i, j, m1.getMatrix()[i][j] + m2.getMatrix()[i][j]);
                }else
                    rez.setMatrix(i, j, m1.getMatrix()[i][j] + m2.getMatrix()[i][j]);

            }
        }

    }


}

