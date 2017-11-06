/**
 * Created by glinut on 11/7/2017.
 */
public class Main {
    public static void main(String[] args) {
        ProducerConsumer pc = new ProducerConsumer(1000, 2000, 50, 20, 20, 10);
        try {
            pc.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
