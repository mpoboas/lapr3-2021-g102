package lapr.project.utils;

import lapr.project.model.Port;

import java.util.Comparator;

public class PortByLatitude implements Comparator<Port> {
    @Override
    public int compare(Port o1, Port o2) {
        int res = Double.compare(o1.getLatitude(), o2.getLatitude());
        if (res == 0)
            res = Double.compare(o1.getLongitude(), o2.getLongitude());

        return res;
    }
}
