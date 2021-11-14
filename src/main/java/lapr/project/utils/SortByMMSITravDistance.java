package lapr.project.utils;

import lapr.project.model.Ship;

import java.util.Comparator;

public class SortByMMSITravDistance implements Comparator<Pair<Ship, Ship>> {
    /**
     * Sorts by ship´s MMSI and travelled distance difference (desc)
     *
     * @return integer comparison value
     */
    public int compare(Pair<Ship, Ship> o1, Pair<Ship, Ship> o2) {
        double totalDistanceDiff1 = o1.getFirst().getTotalDistance() - o1.getSecond().getTotalDistance();
        double totalDistanceDiff2 = o2.getFirst().getTotalDistance() - o2.getSecond().getTotalDistance();

        int mmsiCompare = o1.getFirst().compareTo(o2.getFirst());
        int totalDistDiffCompare = Double.compare(totalDistanceDiff2, totalDistanceDiff1);

        return (mmsiCompare == 0) ? totalDistDiffCompare : mmsiCompare;
    }
}
