package model;

import java.util.ArrayList;

/**
 * Created by glinut on 10/22/2017.
 * The class that allows executing a set of operations in parallel this way from our initial list of
 * operations we execute from a start index to a stop index given bu the user
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
