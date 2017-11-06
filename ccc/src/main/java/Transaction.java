/**
 * Created by glinut on 10/31/2017.
 */
public class Transaction {
    public Long time;
    public String name1;
    public String name2;
    public Long balance;

    public Transaction(Long time, String name1, String name2, Long balance) {
        this.time = time;
        this.name1 = name1;
        this.name2 = name2;
        this.balance = balance;
    }

    public Transaction(Long time,String name){
        this.time = time;
        this.name1 = name;
    }


}
