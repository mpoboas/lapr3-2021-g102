package lapr.project.utils;

import lapr.project.model.Ship;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Comparator;

public class SortByMMSITravDistance implements Comparator<Pair<Ship, Ship>> {
    public int compare(Pair<Ship, Ship> o1, Pair<Ship, Ship> o2) {
        double totalDistanceDiff1 = o1.getKey().getTotalDistance() - o1.getValue().getTotalDistance();
        double totalDistanceDiff2 = o2.getKey().getTotalDistance() - o2.getValue().getTotalDistance();

        int mmsiCompare = o1.getKey().compareTo(o2.getKey());
        int totalDistDiffCompare = Double.compare(totalDistanceDiff2, totalDistanceDiff1);

        return (mmsiCompare == 0) ? totalDistDiffCompare : mmsiCompare;
    }
}
