package lapr.project.utils;

public class Conversions {
    /**
     * Converts nautical miles double value to kilometers
     *
     * @return double value
     */
    public static double nauticalMilesToKm(double nMDistance) {
        return nMDistance * 1.852;
    }
}
