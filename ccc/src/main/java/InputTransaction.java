/**
 * Created by glinut on 10/31/2017.
 */
public class InputTransaction {
    public String id;
    public Long amount;
    public Long time;
    public String owner;

    public InputTransaction(String id, Long amount, Long time, String owner) {
        this.id = id;
        this.amount = amount;
        this.time = time;
        this.owner = owner;
    }
}
