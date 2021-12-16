package lapr.project.model;

import java.util.Objects;

/**
 * This class represents the border between two countries
 */
public class Border {

    /**
     * Country 1
     */
    private final Country country1;
    /**
     * Country 2
     */
    private final Country country2;

    public Border(Country c1,Country c2){
        country1 = c1;
        country2 = c2;
    }

    public Country getCountry1() {
        return country1;
    }

    public Country getCountry2() {
        return country2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Border border = (Border) o;
        return Objects.equals(country1, border.country1) && Objects.equals(country2, border.country2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country1, country2);
    }
}
