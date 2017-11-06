import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by glinut on 10/22/2017.
 */
public class ProgramInternalForm {
    private List<Entry> pif = new ArrayList<>();
    public ProgramInternalForm() {
    }

    public void addWord(Entry entry) {
        pif.add(entry);
    }

    @Override
    public String toString() {
        System.out.println("CODE  :  POSITION");
        System.out.println("-----------------");
        Iterator var2 = this.pif.iterator();

        while(var2.hasNext()) {
            Entry entry = (Entry)var2.next();
            System.out.println(entry);
        }
        return "OK";
    }
}
