package scanner;

import finiteAutomata.FA;
import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by glinut on 11/23/2017.
 */
public class LexicalScanner {
    private static final String IDENTIFIER_AUTOMATA_FILE = "identifierAutomata.in";
    private static final String CONSTANT_AUTOMATA_FILE = "constantAutomata.in";
    private static final String SPECIAL_CHARACTERS_FILE = "specialCharacters.csv";
    private static final String KEYWORDS_FILE = "keywords.csv";

    private List<Integer> lineNumbers;
    private List<Integer> beggingPositions;
    private Set<String> specialCharacters;
    private Set<String> keyWords;
    private CodesMap codesMap;
    private Integer currentCodeTS = 0;
    private FA identifierAutomata;
    private FA constantAutomata;

    /**
     * Initializes the codes for special symbols in the language
     * Loads the keywords from resource KEYWORDS_FILE
     * Loads the special characters from resource file SPECIAL_CHARACTERS_FILE
     * Initializes the identifier and constant automata
     *
     * @throws IOException
     */
    public LexicalScanner() throws IOException {
        this.codesMap = new CodesMap();
        this.keyWords = loadKeywords();
        this.specialCharacters = loadSpecialCharacters();
        this.identifierAutomata = new FA(IDENTIFIER_AUTOMATA_FILE);
        this.constantAutomata = new FA(CONSTANT_AUTOMATA_FILE);
    }

    /**
     * Reads from a file the special characters
     *
     * @return
     * @throws FileNotFoundException
     */
    private Set<String> loadSpecialCharacters() throws FileNotFoundException {
        return new BufferedReader(new FileReader(SPECIAL_CHARACTERS_FILE))
                .lines()
                .collect(Collectors.toSet());
    }

    /**
     * Reads from a file the keywords
     *
     * @return
     * @throws FileNotFoundException
     */
    private Set<String> loadKeywords() throws FileNotFoundException {
        return new BufferedReader(new FileReader(KEYWORDS_FILE))
                .lines()
                .collect(Collectors.toSet());
    }

    public Pair<List<Pair<Integer, Integer>>, Map<String, Integer>> scan(BufferedReader reader) {
        beggingPositions = new ArrayList<>();
        lineNumbers = new ArrayList<>();
        currentCodeTS = 0;
        List<Pair<Integer, Integer>> pif = new ArrayList<>();

        Map<String, Integer> symbolTable = new TreeMap<>();
        List<String> lines = reader.lines().collect(Collectors.toList());
        int lineNo = 0;
        for (String line : lines) {
            for (int i = 0; i < line.length(); ++i) {
                char ch = line.charAt(i);
                int after = 0;
                if (ch == ' ') continue;
                if (Character.isLetter(ch)) {
                    after = identifierAutomata.findSequence(i, line);
                    String word = line.substring(i, after);


                    if (keyWords.contains(word)) {
                        pif.add(Pair.of(codesMap.getCode(word), 0));
                    } else {
                        if (word.length() > 250)
                            throw new RuntimeException("This identifier is longer than 250 characters");
                        symbolTable.computeIfAbsent(word, k -> ++currentCodeTS);
                        pif.add(Pair.of(codesMap.getIdCode(), symbolTable.get(word)));
                    }
                } else if (Character.isDigit(ch)) {
                    after = constantAutomata.findSequence(i, line);
                    String constValue = line.substring(i, after);

                    symbolTable.computeIfAbsent(constValue, k -> ++currentCodeTS);
                    pif.add(Pair.of(codesMap.getConstCode(), symbolTable.get(constValue)));
                } else if (specialCharacters.contains("" + ch)) {
                    after = getIdxAfter(line, i, c -> specialCharacters.contains("" + c));
                    String element = line.substring(i, after);
                    element = element.trim();

                    pif.add(Pair.of(codesMap.getCode(element), 0));
                } else {
                    throw new RuntimeException(String.format("This charachter (at position %d) %s is not permitted!", i, line.charAt(i)));
                }
                lineNumbers.add(lineNo);
                beggingPositions.add(i);
                i = after - 1;
            }
            ++lineNo;
        }
        verifyPIF(pif);

        System.out.println("DONE");

        return Pair.of(pif, symbolTable);
    }


    /**
     * Verifies if a PIF is correct and if there has been any errors in the lexical analysis
     * If there are 2 consecutive values in the PIF then it means that there was an error.
     * Throws exception if error, containing the line and column in the source file at which the error occured.
     *
     * @param pif
     */
    public void verifyPIF(List<Pair<Integer, Integer>> pif) {
        pif = pif.stream().filter(t->t.getKey()!=null).collect(Collectors.toList());
        for (int i = 0; i < pif.size() - 1; ++i) {
//            if(pif.get(i).getValue()!=0 && pif.get(i+1).getValue()!=0)
//                throw new RuntimeException(String.format("There is inconsistency in the file line %d at %d",lineNumbers.get(i)+1,begPositions.get(i)+1));

        }
    }

    /**
     * Finds the last character which is valid in the current continuous string according with a verifier
     *
     * @param line
     * @param i
     * @param verifier
     * @return
     */
    private int getIdxAfter(String line, int i, Predicate<Character> verifier) {

        int idx = i;
        while (idx < line.length() && verifier.test(line.charAt(idx))) ++idx;
        return idx;
    }
}

