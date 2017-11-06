import java.io.*;
import java.util.*;

/**
 * Created by glinut on 10/31/2017.
 */
public class Main {
    public static void main(String args[]) throws IOException {
        HashMap<String,Account> map = new HashMap<>();
        ArrayList<String> names = new ArrayList<>();
        ArrayList<Transaction> transactions = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader("ccc.in"));
        Integer nr = Integer.parseInt(br.readLine());
//        for (int i=0;i<nr;i++){
//            String line = br.readLine();
//            String[] lineSplit = line.split(" ");
//            names.add(lineSplit[0]);
//            map.put(lineSplit[0],new Account(lineSplit[0],Long.parseLong(lineSplit[1])));
//        }
//        Integer nr2 = Integer.parseInt(br.readLine());
//        for (int i=0;i<nr2;i++){
//            String line = br.readLine();
//            String[] lineSplit = line.split(" ");
//            transactions.add(new Transaction(Long.parseLong(lineSplit[3]),lineSplit[0],lineSplit[1],Long.parseLong(lineSplit[2])));
//        }
//        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("ccc.out")));
//        for (Transaction t:transactions){
//            Account source = map.get(t.name1);
//            Account dest = map.get(t.name2);
//            source.balance -= t.balance;
//            dest.balance += t.balance;
//        }
//        writer.write(nr+"");
//        writer.newLine();
//        for (String name:names){
//            writer.write(name+" "+map.get(name).balance);
//            writer.newLine();
//        }
//        writer.close();
        Integer total =0;
        for (int i=0;i<nr;i++){
            String line = br.readLine();
            String[] lineSplit = line.split(" ");
            if (checkNumber(lineSplit[1])){
                names.add(lineSplit[1]);
                total++;
                map.put(lineSplit[1], new Account(lineSplit[0], Long.parseLong(lineSplit[2]), Long.parseLong(lineSplit[3]), lineSplit[1]));
            }
        }
        Integer nr2 = Integer.parseInt(br.readLine());
        for (int i=0;i<nr2;i++){
            String line = br.readLine();
            String[] lineSplit = line.split(" ");
            if (map.containsKey(lineSplit[0])&& map.containsKey(lineSplit[1])) {
                transactions.add(new Transaction(Long.parseLong(lineSplit[3]), lineSplit[0], lineSplit[1], Long.parseLong(lineSplit[2])));
            }
        }
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("ccc.out")));
        Collections.sort(transactions, new Comparator<Transaction>() {
            @Override
            public int compare(Transaction o1, Transaction o2) {
                return (int)(o1.time-o2.time);
            }
        });
        for (Transaction t:transactions){
            Account source = map.get(t.name1);
            if (source.balance+source.limit-t.balance>=0) {
                Account dest = map.get(t.name2);
                source.balance -= t.balance;
                dest.balance += t.balance;
            }
        }
        writer.write(total+"");
        writer.newLine();
        for (String name:names){
            writer.write(map.get(name).name+" "+map.get(name).balance);
            writer.newLine();
        }
        writer.close();

    }

    public static Integer checkSum(String id){
        Integer sum = 67 + 65 + 84 +2* 48;
        for (int i=0;i<id.length();i++){
            sum += (int) id.charAt(i);
        }
        sum = sum % 97;
        sum = 98 - sum;
        return sum;
    }
    public static boolean checkId(String id){
        char[] chars = id.toCharArray();
        Arrays.sort(chars);
        String sorted = new String(chars);
        for (int i=0;i<5;i++){
            if (sorted.charAt(i)!=Character.toUpperCase(sorted.charAt(i+5))){
                return false;
            }
        }
        return  true;
    }
    public static boolean checkNumber(String number){
        if (number.length()==15) {
            if ("CAT".equals(number.substring(0, 3))) {
                String check = number.substring(3,5);
                String id = number.substring(5);
                if (checkId(id)){
                    Integer checkSum = checkSum(id);
                    if (Integer.parseInt(check)==checkSum){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
