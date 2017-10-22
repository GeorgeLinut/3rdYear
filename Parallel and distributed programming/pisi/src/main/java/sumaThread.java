/**
 * Created by glinut on 10/15/2017.
 */
public class sumaThread implements Runnable {
    protected int startLinii, startColoane, stopLinii, stopColoane;
    protected Matrice m1, m2, rez;

    public sumaThread(int startLinii, int startColoane, int stopLinii, int stopColoane, Matrice m1, Matrice m2, Matrice rez) {
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
                        rez.setMatrice(i,j,m1.getMatrice()[i][j] + m2.getMatrice()[i][j]);
                } else if (i == stopLinii) {
                    if (j <= stopColoane)
                        rez.setMatrice(i, j, m1.getMatrice()[i][j] + m2.getMatrice()[i][j]);
                }else
                    rez.setMatrice(i, j, m1.getMatrice()[i][j] + m2.getMatrice()[i][j]);

            }
        }

    }


}
