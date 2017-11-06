/**
 * Created by glinut on 10/31/2017.
 */
public class Account {
    public String name;
    public Long balance;
    public Long limit;
    public String accNumber;

    public Account(String name, Long balance) {
        this.name = name;
        this.balance = balance;
    }

    public Account(String name, Long balance, Long limit, String accNumber) {
        this.name = name;
        this.balance = balance;
        this.limit = limit;
        this.accNumber = accNumber;
    }

}
