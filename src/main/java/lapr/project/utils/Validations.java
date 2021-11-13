package lapr.project.utils;

import lapr.project.model.AISMessage;

public class Validations {
    public static boolean validateAISMessageForCalculations(AISMessage aisMessage) {
        return validateCoordinates(aisMessage.getCoordinates()) && validateHeading(aisMessage.getHeading());
    }

    private static boolean validateCoordinates(Coordinates coordinates) {
        double latitude = coordinates.getLatitude();
        double longitude = coordinates.getLongitude();

        return latitude != 91 && longitude != 181;
    }

    private static boolean validateHeading(double heading) {
        return heading != 511;
    }
}
