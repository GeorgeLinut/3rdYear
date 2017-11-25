package scanner;

import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by glinut on 11/23/2017.
 */
public class CodesMap {

    private static final String KEYWORDS_FILENAME = "codes.csv";
    private static final String DELIM = " ";

    private static final String ID_KEY = "id";
    private static final String CONST_KEY = "const";

    private Map<String, Integer> dictionary;

    public CodesMap() throws IOException {
        this.dictionary = loadData();
    }

    private Map<String,Integer> loadData() throws IOException {
        try(BufferedReader reader = new BufferedReader(
                new FileReader(KEYWORDS_FILENAME))){
                return reader
                        .lines()
                        .map(line-> Pair.of(line.split(DELIM)[0],Integer.parseInt(line.split(DELIM)[1])))
                        .collect(Collectors.toMap(Pair::getKey,Pair::getValue));
        }

    }

    public Integer getCode(String key) {
        return dictionary.get(key);
    }

    public boolean contains(char character) {
        return dictionary.containsKey("" + character);
    }

    public boolean contains(String string) {
        return dictionary.containsKey(string);
    }

    public Integer getIdCode() {
        return dictionary.get(ID_KEY);
    }

    public Integer getConstCode() {
        return dictionary.get(CONST_KEY);
    }
}
