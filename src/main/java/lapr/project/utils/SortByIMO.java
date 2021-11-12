package lapr.project.utils;

import lapr.project.model.Ship;

import java.util.Comparator;

public class SortByIMO implements Comparator<Ship> {
    @Override
    public int compare(Ship o1, Ship o2) {
        if (o1.getIMO().compareTo(o2.getIMO())<0)
            return -1;
        if (o1.getIMO().compareTo(o2.getIMO())>0)
            return 1;
        return 0;
    }
}
