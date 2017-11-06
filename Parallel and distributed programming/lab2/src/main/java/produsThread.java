public class produsThread implements Runnable{
    protected Matrice matrice1, matrice2, matrice3;
    protected int iStart, jStart, iStop, jStop;

    public produsThread(Matrice matrice1, Matrice matrice2, Matrice matrice3,
                       int iStart, int jStart, int iStop, int jStop) {
        this.matrice1 = matrice1;
        this.matrice2 = matrice2;
        this.matrice3 = matrice3;
        this.iStart = iStart;
        this.iStop = iStop;
        this.jStart = jStart;
        this.jStop = jStop;
    }

    @Override
    public void run() {
        for (int i = iStart; i <= iStop; ++i) {
            if(i>=matrice1.getLinii()){
                return;
            }
            for (int j = 0; j < matrice2.getColoane(); ++j) {
                if (i == iStart) {
                    if (j >= jStart) {
                        int res = 0;
                        for (int index = 0; index < matrice1.getColoane(); ++index) {
                            res += (matrice1.getMatrice()[i][index] * matrice2.getMatrice()[index][j]);
                        }
                        matrice3.setMatrice(i,j,res);
                    }
                } else if (i == iStop) {
                    if (j <= jStop) {
                        int res = 0;
                        for (int index = 0; index < matrice1.getColoane(); ++index) {
                            res += (matrice1.getMatrice()[i][index] * matrice2.getMatrice()[index][j]);
                        }
                        matrice3.setMatrice(i,j,res);
                    }
                }else{ int res = 0;
                    for (int index = 0; index < matrice1.getColoane(); ++index) {
                        res += (matrice1.getMatrice()[i][index] * matrice2.getMatrice()[index][j]);
                    }
                    matrice3.setMatrice(i,j,res);}
            }
        }
    }
}

