package lapr.project.utils;

public class Pair<T, S> {
    /**
     * First element of the pair
     */
    private final T first;
    /**
     * Second element of the pair
     */
    private final S second;

    /**
     * Pair empty constructor
     */
    public Pair() {
        this.first = null;
        this.second = null;
    }

    /**
     * Pair constructor
     *
     * @param firstElement  first element
     * @param secondElement second element
     */
    public Pair(T firstElement, S secondElement) {
        this.first = firstElement;
        this.second = secondElement;
    }

    /**
     * Returns first element of the pair
     *
     * @return T generic type instance
     */
    public T getFirst() {
        return first;
    }

    /**
     * Returns second element of the pair
     *
     * @return S generic type instance
     */
    public S getSecond() {
        return second;
    }

    /**
     * Verifies if the object is equal to another received by parameter
     *
     * @return boolean value
     */
    @Override
    public boolean equals(Object otherObject) {
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;

        Pair<?, ?> otherPair = (Pair<?, ?>) otherObject;

        assert this.first != null;
        if (!this.first.equals(otherPair.first)) return false;
        assert second != null;
        return (second.equals(otherPair.second));
    }

    /**
     * Returns a string value of the pair
     *
     * @return String value
     */
    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}
