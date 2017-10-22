package model;


import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by glinut on 10/9/2017.
 */
public class Operation {
    private static final AtomicLong count = new AtomicLong(0);
    private Long id;
    private Account source;
    private Account destination;
    private long amount;

    public Operation(Account source, Account destination, long amount) {
        this.id = count.incrementAndGet();
        this.source = source;
        this.destination = destination;
        this.amount = amount;
    }

    public Account getSource() {
        return source;
    }

    public void setSource(Account source) {
        this.source = source;
    }

    public Account getDestination() {
        return destination;
    }

    public void setDestination(Account destination) {
        this.destination = destination;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void doOperation(){
        source.changeMoney(destination,amount);
    }


}
