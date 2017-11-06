import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by glinut on 10/22/2017.
 */
public class Scanner {
    public static void main(String[] args) {


        try {
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            HashMap code = new HashMap();

            String line;
            while((line = br.readLine()) != null) {
                StringTokenizer tk = new StringTokenizer(line);
                String token = tk.nextToken(" ");
                int tokenNumber = Integer.parseInt(tk.nextToken(" "));
                code.put(token, Integer.valueOf(tokenNumber));
            }

            TreeMap symTable = new TreeMap();
            BufferedReader bufferedReader = new BufferedReader(new FileReader("javaCode.txt"));
            ProgramInternalForm pif = new ProgramInternalForm();
            int lineNumber = 0;
            int index = 0;

            Integer current,symbolTableIndex=0;
            int lineWordCount;
            String row;
            while((row = bufferedReader.readLine()) != null) {
                ++lineNumber;
                String[] stringArray = row.split("\\s+");
                lineWordCount = stringArray.length;

                for(current = 0; current < lineWordCount; ++current) {
                    String s = stringArray[current];
                    if (!s.equals("")) {
                        if (code.containsKey(s)) {
                            Entry entry = new Entry(((Integer) code.get(s)).intValue(), 0);
                            pif.addWord(entry);
                        } else {
                            Pattern ident = Pattern.compile("^[a-zA-Z]+[a-zA-Z0-9]*$");
                            Pattern consta = Pattern.compile("^[+-]?(\\d*\\.)?\\d+$");
                            Matcher m1 = ident.matcher(s);
                            Matcher m2 = consta.matcher(s);
                            boolean b1 = m1.matches();
                            boolean b2 = m2.matches();
                            if ((b1 || b2) && s.length() <= 250) {
                                if (!symTable.containsKey(s)) {
                                    ++symbolTableIndex;
                                    symTable.put(s, symbolTableIndex);
                                }

                                pif.addWord(new Entry(0, ((Integer) symTable.get(s)).intValue()));
                            } else {
                                System.out.println("error line " + lineNumber + "  word: " + s);
                            }
                        }
                    }
                }
            }

            System.out.println("SYMBOL TABLE:");
            System.out.println(".............................");
            Object[] keys = symTable.keySet().toArray();
            Object[] symbols = keys;
            int symbolsNumber = keys.length;

            for(index = 0; index < symbolsNumber; ++index) {
                Object key = symbols[index];
                System.out.println(key + ": " + symTable.get(key));
            }

            System.out.println();
            pif.toString();
        } catch (IOException ioe) {
            System.out.println("Error in reading " + ioe);
        }

    }


}
