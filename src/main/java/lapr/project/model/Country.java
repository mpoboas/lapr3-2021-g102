package lapr.project.model;

import java.util.Objects;

/**
 * This class represents the country and the capital of that country
 */
public class Country extends Place  {

    /**
     * Continent where the country is
     */
    //private final String continent;
    /**
     * The alpha code with 2 letters
     */
    private final String alpha2Code;
    /**
     * The alpha code with 3 letters
     */
    private final String alpha3Code;
    /**
     * The country's name
     */
    //private final String country;
    /**
     * Population of the country
     */
    private final double population;
    /**
     * Capital of the country
     */
    private final String capital;
    /**
     * Latitude of the capital
     */
    //private final double latitude; // eixo y
    /**
     * Longitude of the country
     */
    //private final double longitude; //eixo x

    public Country(String continent,String alpha2,String alpha3, String country,double population,String capital, double longitude, double latitude) {
        super(continent, country, longitude, latitude);
        //this.continent = continent;
        this.alpha2Code = alpha2;
        this.alpha3Code = alpha3;
        //this.country = country;
        this.population = population;
        this.capital = capital;
        //this.latitude = latitude;
        //this.longitude = longitude;
    }

    public String getAlpha2Code() {
        return alpha2Code;
    }

    public String getAlpha3Code() {
        return alpha3Code;
    }


    public String getContinent() {
        return super.getContinent();
    }


    public String getCountry() {
        return super.getCountry();
    }


    public double getLatitude() {
        return super.getLatitude();
    }


    public double getLongitude() {
        return super.getLongitude();
    }

    public double getPopulation() {
        return population;
    }

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


}
