package finiteAutomata;

import finiteAutomata.State;

/**
 * Created by glinut on 11/17/2017.
 */
public class Transition {

    private State firstState;
    private State secondState;
    private String alphabetSymbol;

    public Transition() {
        this.firstState = new State();
        this.secondState = new State();
        this.alphabetSymbol = "";
    }

    public Transition(State firstState, State secondState, String alphabetSymbol) {
        this.firstState = firstState;
        this.secondState = secondState;
        this.alphabetSymbol = alphabetSymbol;
    }

    public State getFirstState() {
        return firstState;
    }

    public void setFirstState(State firstState) {
        this.firstState = firstState;
    }

    public State getSecondState() {
        return secondState;
    }

    public void setSecondState(State secondState) {
        this.secondState = secondState;
    }

    public String getAlphabetSymbol() {
        return alphabetSymbol;
    }

    public void setAlphabetSymbol(String alphabetSymbol) {
        this.alphabetSymbol = alphabetSymbol;
    }

    @Override
    public String toString() {
        return "finiteAutomata.Transition{" +
                "firstState=" + firstState +
                ", secondState=" + secondState +
                ", alphabetSymbol='" + alphabetSymbol + '\'' +
                '}';
    }
}

