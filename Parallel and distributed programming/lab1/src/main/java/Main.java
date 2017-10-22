import model.Account;
import model.ChangeBalanceThread;
import model.CheckSumThread;
import model.Operation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by glinut on 10/9/2017.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Random random = new Random();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Integer numberOfAcc = Integer.parseInt(br.readLine().split(" ")[0]);
        ArrayList<Account> accounts = new ArrayList<>();
        long initialSum = 0;
        for (int i = 0; i < numberOfAcc; i++) {
            long ammount = random.nextInt(10000);
            initialSum+=ammount;
            accounts.add(new Account( ammount));
        }

        int nrTransactions = Integer.parseInt(br.readLine());

        ArrayList<Operation> operations = new ArrayList<>();
        int id1,id2=1;
        for (int i = 0; i < nrTransactions; i++) {
            long ammount = random.nextInt(1000);
            id1 = random.nextInt(numberOfAcc);
            id2 = random.nextInt(numberOfAcc);
            while (id1==id2) {
                id2 = random.nextInt(numberOfAcc);
            }
            Operation operation = new Operation(accounts.get(id1),accounts.get(id2), ammount);
            operations.add(operation);
        }

        int nrThreads = Integer.parseInt(br.readLine());

        int operatiiThread = nrTransactions / nrThreads;
        int rest = nrTransactions % nrThreads;

        ExecutorService service = Executors.newFixedThreadPool(nrThreads);

        long start = System.currentTimeMillis();

        int iStart = 0, iStop = 0;
        for (int i = 1; i <= nrThreads; ++i) {
            int operatiiFinal;
            if (rest > 0) {
                operatiiFinal = operatiiThread + 1;
                rest--;
            } else {
                operatiiFinal = operatiiThread;
            }

            while (operatiiFinal != 0) {
                operatiiFinal--;
                iStop++;
            }

            final int  ii = iStart, iii = iStop+1;

            service.execute(new ChangeBalanceThread(operations,iStart,iStop));
            iStart = iStop;

        }
        service.execute(new CheckSumThread(accounts,initialSum,nrTransactions/10));
        service.shutdown();
        try {
            service.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {

        }



        long stop = System.currentTimeMillis();
        long finalSum = 0;
        for (Account ac: accounts){
            finalSum+=ac.getBalance();
        }
        System.out.println("Initial sum: "+ initialSum);
        System.out.println("Final sum: "+ finalSum);
        System.out.println("FRAUD: "+ (finalSum-initialSum));
        System.out.println("TIME: " + (stop - start));
        System.out.println(start);
        System.out.println(stop);
    }

}
