package mock;

/**
 * Created by glinut on 10/31/2017.
 */
public class Transaction {
    public String name1;
    public String name2;
    public Long time;
    public Long balance;

    public Transaction(String name1, String name2, Long time,Long balance) {
        this.name1 = name1;
        this.name2 = name2;
        this.time = time;
        this.balance = balance;
    }
}
