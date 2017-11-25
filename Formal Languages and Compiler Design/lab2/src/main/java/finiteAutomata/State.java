package finiteAutomata;

/**
 * Created by glinut on 11/17/2017.
 */
public class State {

    private String name;

    public State() {
        this.name = "";
    }

    public State(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        return name.equals(state.name);
    }

    @Override
    public String toString() {
        return "finiteAutomata.State{" +
                "name='" + name + '\'' +
                '}';
    }
}

