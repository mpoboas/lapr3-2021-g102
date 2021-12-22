package lapr.project.model;

import java.util.Objects;

/**
 * This class represents the country and the capital of that country
 */
public class Country extends Place {
    /**
     * The alpha code with 2 letters
     */
    private final String alpha2Code;
    /**
     * The alpha code with 3 letters
     */
    private final String alpha3Code;
    /**
     * Population of the country
     */
    private final double population;
    /**
     * Capital of the country
     */
    private final String capital;

    /**
     * Constructor of the Country,
     * through a continent, an alpha2 code, an alpha3 code, a country name, a population, a capital name, a longitude and a latidude.
     */
    public Country(String continent, String alpha2, String alpha3, String country, double population, String capital, double longitude, double latitude) {
        super(continent, country, longitude, latitude);
        this.alpha2Code = alpha2;
        this.alpha3Code = alpha3;
        this.population = population;
        this.capital = capital;
    }

    /**
     * Gets the country´s alpha2 code
     */
    public String getAlpha2Code() {
        return this.alpha2Code;
    }

    /**
     * Gets the country´s alpha3 code
     */
    public String getAlpha3Code() {
        return this.alpha3Code;
    }

    /**
     * Gets the country´s continent name
     */
    public String getContinent() {
        return super.getContinent();
    }

    /**
     * Gets the country´s name
     */
    public String getCountry() {
        return super.getCountry();
    }

    /**
     * Gets the country´s latitude
     */
    public double getLatitude() {
        return super.getLatitude();
    }

    /**
     * Gets the country´s longitude
     */
    public double getLongitude() {
        return super.getLongitude();
    }

    /**
     * Gets the country´s population
     */
    public double getPopulation() {
        return population;
    }

    /**
     * Gets the country´s capital name
     */
    public String getCapital() {
        return capital;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country1 = (Country) o;
        return Objects.equals(alpha2Code, country1.alpha2Code) && Objects.equals(alpha3Code, country1.alpha3Code) && Objects.equals(getCountry(), country1.getCountry());
    }

    @Override
    public int hashCode() {
        return Objects.hash(alpha2Code, alpha3Code, getCountry());
    }

    @Override
    public String toString() {
        return "Country with Alpha2 code: " + this.alpha2Code + "; " +
                "Alpha3 code: " + this.alpha3Code + "; " +
                "Capital: " + this.capital + "; " +
                "Population: " + this.population;
    }
}
