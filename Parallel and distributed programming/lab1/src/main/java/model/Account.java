package model;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by glinut on 10/9/2017.
 */
public class Account {
    private long balance;
    private ArrayList<LogEntry> log;
    public ReentrantLock lock = new ReentrantLock();
    private static final AtomicLong count = new AtomicLong(-1);
    private Long id;

    /*
        Constructor of the account class , takes the balance as parameter and inserts the first log entry as an transfer
        from a null account
     */
    public Account(long balance) {
        this.id = count.incrementAndGet();
        this.balance = balance;
        this.log = new ArrayList<>();
        this.log.add(new LogEntry(null,balance));
    }

    /*
        ChangeMoney method , implements the transaction functionality, adds the amount to the source and substracts it
        from the destination, the locking of the resources is done in the order of their id, in that way we don't have
        to worry about deadlocks.
     */
    public void changeMoney(Account destination,long ammount)
    {
        if (this.id<destination.getId()){
            System.out.println("Locking account: "+id);
            this.lock.lock();
            System.out.println("Locking account: "+destination.getId());
            destination.lock.lock();
        }
        else {
            System.out.println("Locking account: "+destination.getId());
            destination.lock.lock();
            System.out.println("Locking account: "+id);
            this.lock.lock();
        }
        this.balance+=ammount;
        this.addLogEntry(new LogEntry( destination,ammount));
        System.out.println("Updating:" + this.id+" "+ammount);
        destination.setBalance(destination.getBalance()-ammount);
        System.out.println("Updating:" + destination.getId()+" "+(-1)*ammount);
        destination.addLogEntry(new LogEntry(this,ammount*(-1)));
        System.out.println("Unlocking account: "+id);
        this.lock.unlock();
        System.out.println("Unlocking account: "+destination.getId());
        destination.lock.unlock();
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public ArrayList<LogEntry> getLog() {
        return log;
    }

    public void addLogEntry(LogEntry logEntry) {
        this.log.add(logEntry);
    }

    public Long getId() {
        return id;
    }
}
