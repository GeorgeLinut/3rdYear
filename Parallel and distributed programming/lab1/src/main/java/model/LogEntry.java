package model;

import java.math.BigInteger;

/**
 * Created by glinut on 10/9/2017.
 */
public class LogEntry {
    private Account associatedAccount;
    private long amount;

    public LogEntry(Account associatedAccount, long ammount) {
        this.associatedAccount = associatedAccount;
        this.amount = ammount;
    }

    public Account getAssociatedAccount() {
        return associatedAccount;
    }

    public void setAssociatedAccount(Account associatedAccount) {
        this.associatedAccount = associatedAccount;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}
