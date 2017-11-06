package mock;

import java.io.*;
import java.util.*;

/**
 * Created by glinut on 10/31/2017.
 */
public class Shmen {
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
    public static void main(String[] args) throws IOException {
        ArrayList<String> names = new ArrayList<>();
        HashMap<String, Account> map = new HashMap<>();
        ArrayList<Transaction> transactions = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new FileReader("mock.in"));
        Integer nr = Integer.parseInt(bufferedReader.readLine());
//        for (int i = 0; i < nr; i++) {
//            String line = bufferedReader.readLine();
//            String[] lineSplit = line.split(" ");
//            names.add(lineSplit[0]);
//            map.put(lineSplit[0], new Account(lineSplit[0], Long.parseLong(lineSplit[1])));
//        }
//        Integer nr2 = Integer.parseInt(bufferedReader.readLine());
//        for (int i = 0; i < nr2; i++) {
//            String line = bufferedReader.readLine();
//            String[] lineSplit = line.split(" ");
//            transactions.add(new Transaction(lineSplit[0], lineSplit[1], Long.parseLong(lineSplit[3]), Long.parseLong(lineSplit[2])));
//
//        }
//        for (Transaction t:transactions){
//            Account s = map.get(t.name1);
//            Account d = map.get(t.name2);
//            s.balance = s.balance - t.balance;
//            d.balance = d.balance + t.balance;
//        }
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("mock.out")));
//        writer.write(nr+"");
//        writer.newLine();
//        Iterator it = map.entrySet().iterator();
//        while (it.hasNext()){
//            Map.Entry pair = (Map.Entry)it.next();
//            writer.write(pair.getKey() + " "+ ((Account)pair.getValue()).balance.toString());
//            writer.newLine();
//        }
//        for (String name:names){
//            writer.write(name+" "+map.get(name).balance);
//            writer.newLine();
//        }
//        writer.close();
        for (int i=0;i<nr;i++){
            String line = bufferedReader.readLine();

        }
    }
}
