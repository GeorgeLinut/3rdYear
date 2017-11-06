import com.sun.deploy.util.StringUtils;

import java.io.*;
import java.util.*;

/**
 * Created by glinut on 10/31/2017.
 */
public class Main3 {
    public static void main(String args[]) throws IOException {
        HashMap<String,Account> map = new HashMap<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<String> inputs = new ArrayList<>();
        HashMap<String,List<InputTransaction>> map2 = new HashMap<>();
        ArrayList<Transaction> transactions = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("ccc.in"));
        Integer nr = Integer.parseInt(br.readLine());

        for (int i=0;i<nr;i++) {
            String line = br.readLine();
            String[] lineSplit = line.split(" ");
            Long time = Long.parseLong(lineSplit[lineSplit.length - 1]);
            Transaction transaction = new Transaction(time, line);
            names.add(line);
            transactions.add(transaction);
        }
        Collections.sort(transactions, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {
                return (int)(o1.time-o2.time);
            }
        });
        for (int i=0;i<nr;i++)
            {
            String line = transactions.get(i).name1;
            String[] lineSplit = line.split(" ");
            String id = lineSplit[0];
            Long time = Long.parseLong(lineSplit[lineSplit.length-1]);
            Integer nrInp = Integer.parseInt(lineSplit[1]);
            ArrayList<InputTransaction> inputTransactions = new ArrayList<>();
            int p=1;
            int ok=0;
            Long inputSum = 0L;
            for (int j=0;j<nrInp;j++){
                p++;
                InputTransaction inputTransaction = new InputTransaction(lineSplit[p],Long.parseLong(lineSplit[p+2]),time,lineSplit[p+1]);
                if (!inputTransaction.owner.equals("origin")){
                    if (!map2.containsKey(inputTransaction.id)){
                        ok=1;
                        break;
                    }
                }
                List<InputTransaction> checks = map2.get(inputTransaction.id);
                InputTransaction coresp = null;
                if (!inputTransaction.owner.equals("origin")) {
                    for (InputTransaction t : checks) {
                        if (t.owner.equals(lineSplit[p+1]) && (t.amount == Long.parseLong(lineSplit[p + 2]))) {
                            coresp = t;
                        }
                    }
                    if (coresp == null) {
                        ok=1;
                        break;
                    }
                }
                inputTransactions.add(inputTransaction);
                inputSum += Long.parseLong(lineSplit[p+2]);
                //owner condition
                p+=2;
            }
            if (ok==1)
                continue;

                p++;
            Integer nrOut = Integer.parseInt(lineSplit[p]);
            ArrayList<InputTransaction> currentTransactions = new ArrayList<>();
            int t=p;
            Long outputSum = 0L;

            for (int j=0;j<nrOut;j++){
                t++;
                InputTransaction inputTransaction = new InputTransaction(id,Long.parseLong(lineSplit[t+1]),time,lineSplit[t]);
                String name = lineSplit[t];
                for (int tt=t+1;tt<lineSplit.length;tt++){
                    if (name.equals(lineSplit[tt]))
                        ok =1;
                }
                currentTransactions.add(inputTransaction);
                outputSum += Long.parseLong(lineSplit[t+1]);
                t+=1;
            }
            if (ok==1)
                continue;
            if (outputSum .equals( inputSum)){
                for (InputTransaction inputTransaction :inputTransactions){
                    if(!inputTransaction.owner.equals("origin")) {
                        List<InputTransaction> checks = map2.get(inputTransaction.id);
                        int index = -1;

                        for (Integer k = 0; k < checks.size(); k++) {
                            InputTransaction tt = checks.get(k);
                            if (tt.owner.equals(inputTransaction.owner) && (tt.amount.equals(inputTransaction.amount))) {
                                index = k;
                            }
                        }
                        if (index != -1){

                            checks.remove(index);
                    }
                        int a=0;
                }
                }
                map2.put(id,currentTransactions);
                inputs.add(line);
            }

        }


        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("ccc.out")));
        writer.write(inputs.size()+" ");
        writer.newLine();
        for (String inp :names){
            int pp=0;
            for (String in :inputs){
                if (in.equals(inp))
                    pp=1;
            }
            if (pp==1) {
                writer.write(inp);
            }
            writer.newLine();
        }

        writer.close();

    }
}
