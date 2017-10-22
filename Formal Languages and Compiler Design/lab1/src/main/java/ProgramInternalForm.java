import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by glinut on 10/22/2017.
 */
public class ProgramInternalForm {
    private List<Word> pif = new ArrayList<>();
    public ProgramInternalForm() {
    }

    public void addWord(Word word) {
        pif.add(word);
    }

    @Override
    public String toString() {
        System.out.println("CODE  :  POSITION");
        System.out.println("-----------------");
        Iterator var2 = this.pif.iterator();

        while(var2.hasNext()) {
            Word word = (Word)var2.next();
            System.out.println(word);
        }
        return "OK";
    }
}
