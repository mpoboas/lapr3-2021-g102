package lapr.project.utils;

import lapr.project.model.Port;

import java.util.ArrayList;
import java.util.List;

public class PortTree extends KDTree<Port> {


    @Override
    public void buildKDTree(List<Port> elements) {
        root = insert(root, buildKDTree(elements, true), true);
        //root = buildKDTree(elements, true);
    }

    private Node2D<Port> buildKDTree(List<Port> list, boolean divX) {
        List<Port> xy = new ArrayList<>(list);
        if (list.size() == 0)
            return null;
        if (list.size() == 1)
            return new Node2D<>(list.get(0), list.get(0).getCoords(), null, null);

        if (divX)
            xy.sort(new PortByLongitude()); // sorts the list by x
        else
            xy.sort(new PortByLatitude()); // sorts the list by y

        Port porto;
        if (xy.size() % 2 == 0) {
            porto = xy.get(xy.size() / 2 - 1); // gets the median point
        } else {
            porto = xy.get(xy.size() / 2);
        }
        xy.remove(porto);
        List<Port> p1 = xy.subList(0, xy.size() / 2); // divides the list the points with less than the median
        List<Port> p2 = xy.subList(xy.size() / 2, xy.size()); // divides the list the points with more than the median
        Node2D<Port> v = new Node2D<>(porto, porto.getCoords(), null, null); // creates a root of the median
        v.setLeft(buildKDTree(p1,!divX)); // builds the left subtree of the root
        v.setRight(buildKDTree(p2,!divX)); // builds the right subtree of the root
       // v.setLeft(insert(v, buildKDTree(p1, !divX), !divX));
       // v.setRight(insert(v, buildKDTree(p2, !divX), !divX));
        return v;
    }

}
