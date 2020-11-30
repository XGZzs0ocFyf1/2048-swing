package hw3.game;

import java.util.Objects;

public class Key {

    private final int I;
    private final int J;

    public Key(int i, int j) {
        this.I = i;
        this.J = j;
    }

    public int getI() {
        return I;
    }

    public int getJ() {
        return J;
    }

    @Override
    public String toString() {
        return "Key{" +
                "i=" + I +
                ", j=" + J +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key key = (Key) o;
        return I == key.I &&
                J == key.J;
    }

    @Override
    public int hashCode() {
        return Objects.hash(I, J);
    }
}
