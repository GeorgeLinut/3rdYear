package model;

import java.util.ArrayList;

/**
 * Created by glinut on 10/23/2017.
 */
public class CheckSumThread implements Runnable{
    private ArrayList<Account> accounts;
    private long checks;
    private long initialSum;

    public CheckSumThread(ArrayList<Account> accounts, long initialSum,long checks) {
        this.accounts = accounts;
        this.initialSum = initialSum;
        this.checks =checks;
    }

    @Override
    public void run() {
        while (checks!=0) {
            long currentSum = 0;
            for (Account ac : accounts) {
                int accountBalance = 0;
                ArrayList<LogEntry> currentLog = ac.getLog();
                int length = currentLog.size();
                for (int i=0;i<length;i++){
                    currentSum += currentLog.get(i).getAmount();
                }
                currentSum += accountBalance;
            }
            System.out.println("Initial sum: " + initialSum);
            System.out.println("Current sum: " + currentSum);
            System.out.println("FRAUD: " + (currentSum - initialSum));
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            checks--;
        }

    }
}
