package finiteAutomata; /**
 * Created by glinut on 11/17/2017.
 */

import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class FA {


    private List<String> states;
    private Set<String> finalStates;
    private String startState = null;
    private List<String> alphabet;
    private Map<Pair<String, String>, String> transitions;
    private String filename;

    public FA(String filename) throws FileNotFoundException {
        this.filename = filename;
        loadAutomaton();
    }

    /**
     * Runs a helper function to test if the sequence is true or not
     *
     * @param sequence
     * @return
     */
    public int findSequence(int seqIdx, String sequence) {
        return dfsSearch(startState, seqIdx, sequence);
    }

    /**
     * This function moves from one node to another if the letter from the alphabet
     * together with the current state form a transition if not the current position
     * to which we arrived is returned to be used in showing what part of the sequence
     * is valid.
     * By returning the size of the list means that the introduced sequence is true.
     *
     * @param state
     * @param seqIdx
     * @param sequence
     * @return
     */
    private int dfsSearch(String state, int seqIdx, String sequence) {

        if (sequence.length() == seqIdx && finalStates.contains(state))
            return seqIdx;
        if (sequence.length() == seqIdx)
            return seqIdx;
        Pair<String, String> pr = Pair.of(state, Character.toString(sequence.charAt(seqIdx)));
        if (!transitions.containsKey(pr))
            return seqIdx;
        return dfsSearch(transitions.get(pr), seqIdx + 1, sequence);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Start State: ").append(startState).append("\n");
        builder.append("States:\n");
        for (String state : states) {
            builder.append(state).append(" ");
        }
        builder.append("\n");
        builder.append("Final States:\n");
        for (String finalState : finalStates) {
            builder.append(finalState).append(" ");
        }
        builder.append("\n");
        builder.append("Alphabet:\n");
        builder.append(alphabet.toString());
        builder.append("\nTransitions:\n");
        for (Pair<String, String> key : transitions.keySet()) {
            String nextState = transitions.get(key);
            builder.append(key.getLeft()).append(" ");
            builder.append(key.getRight()).append(" ");
            builder.append(nextState).append("\n");
        }
        return builder.toString();
    }

    private void loadAutomaton() throws FileNotFoundException {

        Scanner scanner = new Scanner(getReader());
        states = readStates(scanner);
        finalStates = readFinalStates(scanner);
        startState = scanner.next();
        alphabet = readStates(scanner);
        transitions = readTransitions(scanner);
    }

    private Map<Pair<String, String>, String> readTransitions(Scanner scanner) {

        Map<Pair<String, String>, String> ret = new HashMap<>();
        int noTransitions = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < noTransitions; ++i) {
            String[] line = scanner.nextLine().split(" ");
            Pair<String, String> pr = Pair.of(line[0], line[1]);
            ret.put(pr, line[2]);
        }
        return ret;
    }

    private Set<String> readFinalStates(Scanner scanner) {
        int noStates = scanner.nextInt();
        Set<String> ret = new HashSet<>();
        for (int i = 0; i < noStates; ++i) {
            ret.add(scanner.next());
        }
        return ret;
    }

    private List<String> readStates(Scanner scanner) {
        int noStates = scanner.nextInt();
        List<String> ret = new ArrayList<>(noStates);
        for (int i = 0; i < noStates; ++i) {
            ret.add(scanner.next());
        }
        return ret;
    }

    private BufferedReader getReader() throws FileNotFoundException {
        return new BufferedReader(new FileReader(getFilename()));
    }

    private String getFilename() {
        return this.filename;
    }
}
