package lapr.project.model;

import java.util.Objects;

/**
 * This class represents the connection between ports
 */
public class SeaDist implements Comparable<SeaDist> {

    /**
     * The initial Country
     */
    private final Country from_country;
    /**
     * The initial port
     */
    private final Port from_port;
    /**
     * The destination port
     */
    private final Port to_port;
    /**
     * The destination country
     */
    private final Country to_country;
    /**
     * Distance between the ports
     */
    private final int seaDistance;

    public SeaDist(Country fromC, Port fromP,Country toC, Port toP,int distance){
        from_country = fromC;
        from_port = fromP;
        to_port = toP;
        to_country = toC;
        seaDistance = distance;
    }

    public Country getFrom_country() {
        return from_country;
    }

    public Port getFrom_port() {
        return from_port;
    }

    public Port getTo_port() {
        return to_port;
    }

    public Country getTo_country() {
        return to_country;
    }

    public int getSeaDistance() {
        return seaDistance;
    }

    @Override
    public int compareTo(SeaDist o) {
        return Double.compare(seaDistance,o.seaDistance);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SeaDist seaDist = (SeaDist) o;
        return Objects.equals(from_port, seaDist.from_port) && Objects.equals(to_port, seaDist.to_port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from_port, to_port);
    }
}
