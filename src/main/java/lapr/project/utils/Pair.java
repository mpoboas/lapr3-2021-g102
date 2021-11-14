package lapr.project.utils;

public class Pair<T, S> {
    private final T first;
    private final S second;

    public Pair() {
        this.first = null;
        this.second = null;
    }

    public Pair(T firstElement, S secondElement) {
        this.first = firstElement;
        this.second = secondElement;
    }

    public T getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

    public boolean equals(Object otherObject) {
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;

        Pair<?, ?> otherPair = (Pair<?, ?>) otherObject;

        return (this.first.equals(otherPair.first) && second.equals(otherPair.second));
    }

    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
