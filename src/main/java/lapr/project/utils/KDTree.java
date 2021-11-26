package lapr.project.utils;


import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class KDTree<T extends Comparable<T>> {


    /**
     * Nested static class for a kd tree node.
     */
    protected static class Node2D<T> {
        private Point2D.Double coords;
        private T info;
        private Node2D<T> left;
        private Node2D<T> right;

        public Node2D(T element, Node2D<T> leftChild, Node2D<T> rightChild) {
            info = element;
            left = leftChild;
            right = rightChild;
        }

        public Node2D(T element, Point2D.Double coords, Node2D<T> leftChild, Node2D<T> rightChild) {
            info = element;
            left = leftChild;
            right = rightChild;
            this.coords = coords;
        }

        public Node2D(T element, double x, double y, Node2D<T> leftChild, Node2D<T> rightChild) {
            info = element;
            left = leftChild;
            right = rightChild;
            coords = new Point2D.Double(x, y);
        }

        public T getElement() {
            return info;
        }

        public double getX() {
            return coords.getX();
        }

        public void setX(Double x) {
            coords.x = x;
        }

        public double getY() {
            return coords.getY();
        }

        public void setY(Double y) {
            coords.y = y;
        }

        public void setElement(T element) {
            info = element;
        }

        public void setLeft(Node2D<T> node) {left = node;}
        public void setRight(Node2D<T> node) {right = node;}

    }
    //----------- end of nested Node class -----------

    private final Comparator<Node2D<T>> cmpX = new Comparator<Node2D<T>>() {
        @Override
        public int compare(Node2D o1, Node2D o2) {
            return Double.compare(o1.getX(), o2.getX());
        }
    };

    private final Comparator<Node2D<T>> cmpY = new Comparator<Node2D<T>>() {
        @Override
        public int compare(Node2D<T> o1, Node2D<T> o2) {
            return Double.compare(o1.getY(), o2.getY());
        }
    };

    protected Node2D<T> root;

    public KDTree() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public Node2D<T> getRoot() {
        return root;
    }

    public int size() {
        return size(root);
    }

    private int size(Node2D<T> node) {
        return (node == null) ? 0 : (1 + size(node.left) + size(node.right));
    }

    public int height() {
        return height(root);
    }

    private int height(Node2D<T> node) {
        if (node == null) return -1;
        return (height(node.left) > height(node.right)) ? 1 + height(node.left) : 1 + height(node.right);
    }


    public void insert(T element, double x, double y) {
        root = insert(root, new Node2D<>(element, x, y, null, null), true);

    }

    protected Node2D<T> insert(Node2D<T> currentNode, Node2D<T> node, boolean divX) {

        if (currentNode == null)
            return node;
        if (node.coords.equals(currentNode.coords)) {
            currentNode.setElement(node.info);
        } else {
            int cmpResult = (divX ? cmpX : cmpY).compare(node, currentNode);

            if (cmpResult == -1) {
                if (currentNode.left == null)
                    currentNode.left = node;
                else
                    insert(currentNode.left, node, !divX);
            } else {
                if (currentNode.right == null)
                    currentNode.right = node;
                else
                    insert(currentNode.right, node, !divX);
            }

        }
        return currentNode;
    }

    public void buildKDTree(List<T> elements){}


    public T findNearestNeighbour(double x, double y) {
        return findNearestNeighbour(root, x, y, root, true);
    }

    private T findNearestNeighbour(Node2D<T> node, double x, double y, Node2D<T> closestNode, boolean divX) {
        if (node == null)
            return null;
        double d = Point2D.distanceSq(node.coords.x, node.coords.y, x, y);
        double closestDist = Point2D.distanceSq(closestNode.coords.x, closestNode.coords.y, x, y);

        if (closestDist > d)
            closestNode = node;

        double delta = divX ? (x - node.coords.x) : (y - node.coords.y);
        double deltaS = delta * delta;

        Node2D<T> node1 = delta < 0 ? node.left : node.right;
        Node2D<T> node2 = delta < 0 ? node.right : node.left;

        findNearestNeighbour(node1, x, y, closestNode, !divX);

        if (deltaS < closestDist)
            findNearestNeighbour(node2, x, y, closestNode, !divX);

        return closestNode.info;
    }

    public List<T> inOrder() {
        List<T> list = new ArrayList<>();
        if (root != null)
            inOrderSubtree(root, list);
        return list;
    }

    private void inOrderSubtree(Node2D<T> node, List<T> list) {
        if (node == null)
            return;
        inOrderSubtree(node.left, list);
        list.add(node.getElement());
        inOrderSubtree(node.right, list);
    }

    //#########################################################################

    /**
     * Returns a string representation of the tree.
     * Draw the tree horizontally
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toStringRec(root, 0, sb);
        return sb.toString();
    }

    private void toStringRec(Node2D<T> root, int level, StringBuilder sb) {
        if (root == null)
            return;
        toStringRec(root.right, level + 1, sb);
        if (level != 0) {
            for (int i = 0; i < level - 1; i++)
                sb.append("|\t");
            sb.append("|-------").append(root.getElement()).append("\n");
        } else
            sb.append(root.getElement()).append("\n");
        toStringRec(root.left, level + 1, sb);
    }

}

