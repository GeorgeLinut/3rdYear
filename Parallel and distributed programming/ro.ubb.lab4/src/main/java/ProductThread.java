public class ProductThread implements Runnable {
    protected Matrix matrice1, matrice2, matrice3;
    protected int iStart, jStart, iStop, jStop;

    public ProductThread(Matrix matrice1, Matrix matrice2, Matrix matrice3,
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
        for (int i = iStart; i < iStop; ++i) {
            matrice1.locks.get(i).lock();
            if (i >= matrice1.getLinii()) {
                return;
            }
            for (int j = 0; j < matrice2.getColoane(); ++j) {
                if (i == iStart) {
                    if (j >= jStart) {
                        int res = 0;
                        for (int index = 0; index < matrice1.getColoane(); ++index) {
                            res += (matrice1.getMatrix()[i][index] * matrice2.getMatrix()[index][j]);
                        }
                        matrice3.setMatrix(i, j, res);
                        //matrice3.pump(i);
                    }
                } else if (i == iStop) {
                    if (j <= jStop) {
                        int res = 0;
                        for (int index = 0; index < matrice1.getColoane(); ++index) {
                            res += (matrice1.getMatrix()[i][index] * matrice2.getMatrix()[index][j]);
                        }
                        matrice3.setMatrix(i, j, res);
                        //matrice3.pump(i);
                    }
                } else {
                    int res = 0;
                    for (int index = 0; index < matrice1.getColoane(); ++index) {
                        res += (matrice1.getMatrix()[i][index] * matrice2.getMatrix()[index][j]);
                    }
                    matrice3.setMatrix(i, j, res);
                    //matrice3.pump(i);}
                }
            }
            matrice1.ready.get(i).signal();
            matrice1.locks.get(i).unlock();
        }
    }
}

