/**
 * Created by glinut on 10/22/2017.
 */
public class Word {
    private int code;
    private int position;

    public Word(int code, int position) {
        this.code = code;
        this.position = position;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int cod) {
        this.code = cod;
    }

    public int getposition() {
        return this.position;
    }

    public void setposition(int position) {
        this.position = position;
    }

    public String toString() {
        return "code :" + this.code + " position: " + this.position;
    }
}
