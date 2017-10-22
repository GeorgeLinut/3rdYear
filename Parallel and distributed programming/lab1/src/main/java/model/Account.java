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

    public Account(long balance) {
        this.id = count.incrementAndGet();
        this.balance = balance;
        this.log = new ArrayList<>();
        this.log.add(new LogEntry(null,balance));
    }

    public void changeMoney(Account destination,long ammount)
    {
        if (this.id<destination.getId()){
            this.lock.lock();
            destination.lock.lock();
        }
        else {
            destination.lock.lock();
            this.lock.lock();
        }
        this.balance+=ammount;
        this.addLogEntry(new LogEntry( destination,ammount));
        destination.setBalance(destination.getBalance()-ammount);
        destination.addLogEntry(new LogEntry(this,ammount*(-1)));
        this.lock.unlock();
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
