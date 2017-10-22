package model;

import java.util.ArrayList;

/**
 * Created by glinut on 10/22/2017.
 */
public class ChangeBalanceThread implements Runnable {
    private ArrayList<Operation> operations;
    private int iStart,iStop;

    public ChangeBalanceThread(ArrayList<Operation> operations, int iStart, int iStop) {
        this.operations = operations;
        this.iStart = iStart;
        this.iStop = iStop;
    }

    @Override
    public void run() {
        for (int i= iStart;i<iStop;i++){
            operations.get(i).doOperation();
        }
    }
}
