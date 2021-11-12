package lapr.project.utils;

import lapr.project.model.Ship;

import java.util.Comparator;

public class SortByCallSign implements Comparator<Ship> {
    @Override
    public int compare(Ship o1, Ship o2) {
        if (o1.getCallSign().compareTo(o2.getCallSign()) < 0)
            return -1;
        if (o1.getCallSign().compareTo(o2.getCallSign()) > 0)
            return 1;
        return 0;

    }
}
