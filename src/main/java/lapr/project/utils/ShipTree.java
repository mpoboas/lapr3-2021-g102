package lapr.project.utils;

import lapr.project.model.Ship;

import java.util.Comparator;

public class ShipTree extends BST<Ship>{

    Comparator<Ship> comp;

    public ShipTree(String type){
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

        if (node.getElement().compareTo(element) > 0)
            return find(node.getLeft(), element);

        if (node.getElement().compareTo(element) < 0)
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
            if ( comp.compare(root1.getElement(), root2.getElement()) == 0) {
                return equals(root1.getLeft(), root2.getLeft())
                        && equals(root1.getRight(), root2.getRight());
            } else
                return false;
        } else return false;
    }
}
