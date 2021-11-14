package lapr.project.utils;

import lapr.project.model.AISMessage;
import lapr.project.model.Ship;
import lapr.project.model.Trips;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ShipTree extends BST<Ship> {

    Comparator<Ship> comp;

    public ShipTree(String type) {
        if (type.equals("IMO"))
            comp = new SortByIMO();
        if (type.equals("Call Sign"))
            comp = new SortByCallSign();
        if (type.equals("MMSI"))
            comp = new Comparator<Ship>() {
                @Override
                public int compare(Ship o1, Ship o2) {
                    return o1.compareTo(o2);
                }
            };
    }

    public Ship getShip(Ship ship) {
        Node<Ship> n = find(ship);
        if (n == null)
            return null;
        return n.getElement();
    }

    private int balanceFactor(Node<Ship> node) {
        return height(node.getRight()) - height(node.getLeft());
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    private Node<Ship> rightRotation(Node<Ship> node) {
        Node<Ship> leftson = node.getLeft();
        node.setLeft(leftson.getRight());
        leftson.setRight(node);
        node = leftson;
        return node;

        //throw new UnsupportedOperationException("Not supported yet.");
    }

    private Node<Ship> leftRotation(Node<Ship> node) {
        Node<Ship> rightson = node.getRight();
        node.setRight(rightson.getLeft());
        rightson.setLeft(node);
        node = rightson;
        return node;
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    private Node<Ship> twoRotations(Node<Ship> node) {
        if (balanceFactor(node) < 0) {
            node.setLeft(leftRotation(node.getLeft()));
            node = rightRotation(node);
        } else {
            node.setRight(rightRotation(node.getRight()));
            node = leftRotation(node);
        }
        return node;
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    private Node<Ship> balanceNode(Node<Ship> node) {
        if (balanceFactor(node) > 1)
            if (balanceFactor(node.getRight()) >= 0) {
                //return rightRotation(node);
                return leftRotation(node);
                //rotação simples
            } else {
                return twoRotations(node);
            }
        //rotação dupla
        if (balanceFactor(node) < -1)
            if (balanceFactor(node.getLeft()) < 0) {
                //rotação simples
                //return leftRotation(node);
                return rightRotation(node);
            } else {
                return twoRotations(node);
                //rotação dupla
            }
        //throw new UnsupportedOperationException("Not supported yet.");
        return node;
    }

    @Override
    public void insert(Ship element) {
        root = insert(element, root);
    }

    private Node<Ship> insert(Ship element, Node<Ship> node) {

        if (node == null)
            return new Node(element, null, null);
        if (node.getElement() == element)
            node.setElement(element);
        else if (comp.compare(node.getElement(), element) > 0) {
            node.setLeft(insert(element, node.getLeft()));
            node = balanceNode(node);
        } else {
            node.setRight(insert(element, node.getRight()));
            node = balanceNode(node);
        }
        return node;

        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void remove(Ship element) {
        root = remove(element, root());
    }

    private Node<Ship> remove(Ship element, Node<Ship> node) {

        if (node == null)
            return null;
        if (node.getElement() == element) {
            if (node.getLeft() == null && node.getRight() == null)
                return null;
            if (node.getLeft() == null)
                return node.getRight();
            if (node.getRight() == null)
                return node.getLeft();
            Ship smallElem = smallestElement(node.getRight());
            node.setElement(smallElem);
            node.setRight(remove(smallElem, node.getRight()));
            node = balanceNode(node);
        } else if (comp.compare(node.getElement(), element) > 0) {
            node.setLeft(remove(element, node.getLeft()));
            node = balanceNode(node);
        } else {
            node.setRight(remove(element, node.getRight()));
            node = balanceNode(node);
        }
        return node;
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected Node<Ship> find(Node<Ship> node, Ship element) {
        if (node == null) return null;

        if (comp.compare(node.getElement(), element) > 0)
            return find(node.getLeft(), element);

        if (comp.compare(node.getElement(), element) < 0)
            return find(node.getRight(), element);

        return node;
    }

    public boolean equals(Object otherObj) {
        if (this == otherObj)
            return true;

        if (otherObj == null || this.getClass() != otherObj.getClass())
            return false;

        ShipTree second = (ShipTree) otherObj;
        return equals(root, second.root);
    }

    public boolean equals(Node<Ship> root1, Node<Ship> root2) {
        if (root1 == null && root2 == null)
            return true;
        else if (root1 != null && root2 != null) {
            if (comp.compare(root1.getElement(), root2.getElement()) == 0) {
                return equals(root1.getLeft(), root2.getLeft())
                        && equals(root1.getRight(), root2.getRight());
            } else
                return false;
        } else return false;
    }

    // The use of the following six methods is recommended when the initialization is done with the respective type of data
    public Ship searchShipByMMSI(int mmsi) {
        Node<Ship> n = searchShipByMMSI(root, mmsi);
        if (n == null)
            return null;
        return n.getElement();
    }

    private Node<Ship> searchShipByMMSI(Node<Ship> node, int mmsi) {
        if (node == null) return null;
        if (mmsi == node.getElement().getMMSI()) return node;
        if (mmsi < node.getElement().getMMSI()) return searchShipByMMSI(node.getLeft(), mmsi);
        else return searchShipByMMSI(node.getRight(), mmsi);
    }

    public Ship searchShipByIMO(String IMO) {
        Node<Ship> n = searchShipByIMO(root, IMO);
        if (n == null)
            return null;
        return n.getElement();
    }

    private Node<Ship> searchShipByIMO(Node<Ship> node, String IMO) {
        if (node == null) return null;
        if (IMO.equals(node.getElement().getIMO())) return node;
        if (IMO.compareTo(node.getElement().getIMO()) < 0) return searchShipByIMO(node.getLeft(), IMO);
        else return searchShipByIMO(node.getRight(), IMO);
    }

    public Ship searchShipByCallSign(String callSign) {
        Node<Ship> n = searchShipByCallSign(root, callSign);
        if (n == null)
            return null;
        return n.getElement();
    }

    private Node<Ship> searchShipByCallSign(Node<Ship> node, String callSign) {
        if (node == null) return null;
        if (callSign.equals(node.getElement().getCallSign())) return node;
        if (callSign.compareTo(node.getElement().getCallSign()) < 0)
            return searchShipByCallSign(node.getLeft(), callSign);
        else return searchShipByCallSign(node.getRight(), callSign);
    }

    public List<Pair<Ship, Ship>> getCloseCoordsShips() {
        List<Pair<Ship, Ship>> closeCoordsShipList = new ArrayList<>();
        getCloseCoordsShips(root, closeCoordsShipList);

        return new ArrayList<>(closeCoordsShipList);
    }

    private void getCloseCoordsShips(Node<Ship> node, List<Pair<Ship, Ship>> closeCoordsShipList) {
        if (node == null)
            return;

        getCloseCoordsShips(node.getLeft(), closeCoordsShipList);
        addAllPairs(root, node, closeCoordsShipList);
        getCloseCoordsShips(node.getRight(), closeCoordsShipList);
    }

    private void addAllPairs(Node<Ship> node, Node<Ship> nodeToBeCompared, List<Pair<Ship, Ship>> closeCoordsShipList) {
        if (node == null)
            return;

        addAllPairs(node.getLeft(), nodeToBeCompared, closeCoordsShipList);
        if (node != nodeToBeCompared) {
            if (isPair(nodeToBeCompared, node))
                closeCoordsShipList.add(new Pair<>(nodeToBeCompared.getElement(), node.getElement()));
        }
        addAllPairs(node.getRight(), nodeToBeCompared, closeCoordsShipList);
    }

    private static boolean isPair(Node<Ship> node1, Node<Ship> node2) {
        if (node1 == null || node2 == null) return false;
        // Acceptance Criteria: "Do not consider ships with Travelled Distance less than 10kms; Ships should have different Travalled Distances."
        if (Conversions.nauticalMilesToKm(node1.getElement().getTotalDistance()) < 10 || Conversions.nauticalMilesToKm(node2.getElement().getTotalDistance()) < 10 || (node1.getElement().getTotalDistance() == node2.getElement().getTotalDistance()))
            return false;
        for (Trips ship1Trip : node1.getElement().getTripsList()) { // Iterate through first ship trip list
            AISMessage departureAIS1 = ship1Trip.getAIsMessage(0); // Get the trip´s departure positional message
            AISMessage arrivalAIS1 = ship1Trip.getAIsMessage(ship1Trip.getAISMessageListSize() - 1); // Get the trip´s arrival positional message
            if (Validations.validateAISMessageForCalculations(departureAIS1) && Validations.validateAISMessageForCalculations(arrivalAIS1)) {
                for (Trips ship2Trip : node2.getElement().getTripsList()) { // Iterate through second ship trip list
                    AISMessage departureAIS2 = ship2Trip.getAIsMessage(0); // Get the trip´s departure positional message
                    AISMessage arrivalAIS2 = ship2Trip.getAIsMessage(ship2Trip.getAISMessageListSize() - 1); // Get the trip´s arrival positional message
                    if (Validations.validateAISMessageForCalculations(departureAIS2) && Validations.validateAISMessageForCalculations(arrivalAIS2)) {
                        double distance1 = Conversions.nauticalMilesToKm(departureAIS1.getDistanceAIMes(departureAIS2)); // Distance between both ships´ departure positions
                        double distance2 = Conversions.nauticalMilesToKm(arrivalAIS1.getDistanceAIMes(arrivalAIS2)); // Distance between both ships´ arrival positions
                        if (distance1 <= 5 && distance2 <= 5) return true;
                    }
                }
            }
        }

        return false;
    }
}
