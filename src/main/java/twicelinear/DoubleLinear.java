package twicelinear;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Function;

/**
 * https://www.codewars.com/kata/5672682212c8ecf83e000050
 */
class DoubleLinear {

    public static int dblLinear(int n) {
        if (n == 0) {
            return 1;
        }

        int i = n;

        Stack<Function<Integer, Integer>> lifo = new Stack<>();

        while (i > 0) {
            if (i % 2 == 0) {
                lifo.push(DoubleLinear::z);
                System.out.println("1");
                i = (i - 2) / 2;
            } else {
                i = (i - 1) / 2;
                System.out.println("0");
                lifo.push(DoubleLinear::y);
            }
        }

        int result = 1;

        while (!lifo.empty()) {
            Function<Integer, Integer> function = lifo.pop();
            result = function.apply(result);
        }

        return result;
    }

    static long limit(int n) {
//        return (int)Math.pow(2, log2(n) + 1) - 1;
//        return (long)Math.pow(2, n + 1) - 1;
//        return Math.max(8L, (long)Math.pow(2, log2(n) + 1)) - 1;
        return 256;
    }

    private static int y(int x) {
        return 2 * x + 1;
    }

    private static int z(int x) {
        return 3 * x + 1;
    }
}

class BinaryTree<T extends Comparable<T>> {
    private Node<T> root = null;

    public void add(T element) {
        root = addRecursively(root, element);

        if (root.balance() < -1 || root.balance() > 1) {
            rebalance();
        }
    }

    public long size() {
        return root == null ? 0 : root.size();
    }

    public T get(long index) {
        return searchRecursively(root, index)
                .map(Node::element)
                .orElseThrow(() -> new RuntimeException("Cannot find element by index " + index));
    }

    public boolean contains(T element) {
        return containsNodeRecursively(root, element);
    }

    public Iterator<T> breadthFirst() {
        if (root == null) {
            return new BreadthFirst<>();
        }

        return new BreadthFirst<>(root);
    }

    private Optional<Node<T>> searchRecursively(Node<T> current, long index) {
        if (current == null) {
            return Optional.empty();
        }

        if (current.left() == null && index == 0) {
            return Optional.of(current);
        }
        if (current.left() == null || current.left().size() < index) {
            return searchRecursively(current.right(), index - current.leftSize() - 1);
        }
        if (current.left() != null && current.left().size() > index) {
            return searchRecursively(current.left(), index);
        }

        return Optional.of(current);
    }

    private Node<T> addRecursively(Node<T> current, T element) {
        if (current == null) {
            return new Node<>(element);
        }

        if (element.compareTo(current.element()) < 0) {
            current.left(addRecursively(current.left(), element));

            if (current.left().balance() < -1 || current.left().balance() > 1) {
                rebalance(current, current.left());
            }
        } else if (element.compareTo(current.element()) > 0) {
            current.right(addRecursively(current.right(), element));

            if (current.right().balance() < -1 || current.right().balance() > 1) {
                rebalance(current, current.right());
            }
        }


        return current;
    }

    private boolean containsNodeRecursively(Node<T> current, T element) {
        if (current == null) {
            return false;
        }

        if (element.equals(current.element())) {
            return true;
        }

        if (element.compareTo(current.element()) < 0) {
            return containsNodeRecursively(current.left(), element);
        } else {
            return containsNodeRecursively(current.right(), element);
        }
    }

    private void rebalance() {
        if (root == null) {
            return;
        }

        Node<T> temporaryParent = new Node<>();
        temporaryParent.left(root);
        rebalance(temporaryParent, root);
        root = temporaryParent.left();
    }

    private void rebalance(Node<T> parent, Node<T> child) {
        if (child.balance() >= 2) {
            if (child.right() != null && child.right().balance() >= 1) {
                rotateLeft(parent, child);
            } else if (child.right() != null && child.right().balance() <= -1) {
                rotateRightLeft(parent, child);
            }
        }

        if (child.balance() <= -2) {
            if (child.left() != null && child.left().balance() <= -1) {
                rotateRight(parent, child);
            } else if (child.left() != null && child.left().balance() >= 1) {
                rotateLeftRight(parent, child);
            }
        }
    }

    private void rotateRightLeft(Node<T> parent, Node<T> child) {
        rotateRight(child, child.right());
        rotateLeft(parent, child);
    }

    private void rotateLeftRight(Node<T> parent, Node<T> child) {
        rotateLeft(child, child.left());
        rotateRight(parent, child);
    }

    private void rotateLeft(Node<T> parent, Node<T> child) {
        Node<T> right = child.right();

        child.right(right.left());
        right.left(child);

        if (child == parent.left()) {
            parent.left(right);
        } else {
            parent.right(right);
        }
    }

    private void rotateRight(Node<T> parent, Node<T> child) {
        Node<T> left = child.left();
        child.left(left.right());
        left.right(child);

        if (child == parent.left()) {
            parent.left(left);
        } else {
            parent.right(left);
        }
    }

    /* *****************************************************************************************************************
     * Inner class(es)
     **************************************************************************************************************** */

    private static class BreadthFirst<T> implements Iterator<T> {
        private final Queue<Node<T>> nextNodes = new LinkedList<>();

        BreadthFirst() {
        }

        BreadthFirst(Node<T> rootNode) {
            this.nextNodes.add(rootNode);
        }

        @Override
        public boolean hasNext() {
            return !nextNodes.isEmpty();
        }

        @Override
        public T next() {
            Node<T> currentNode = this.nextNodes.remove();

            if (currentNode.left() != null) {
                nextNodes.add(currentNode.left());
            }

            if (currentNode.right() != null) {
                nextNodes.add(currentNode.right());
            }

            return currentNode.element();
        }
    }
}

class Node<T> {
    private final T element;

    private Node<T> left;
    private Node<T> right;

    private int balance = 0;
    private long size = 0;
    private int height = 0;

    Node(T element) {
        this.element = element;
        this.size = 1;
        this.height = 1;
    }

    Node() {
        this.element = null;
    }

    public Node<T> left() {
        return left;
    }

    public void left(Node<T> left) {
        this.left = left;
        this.height = Math.max(leftHeight(), rightHeight()) + 1;
        this.balance = rightHeight() - leftHeight();
        this.size = leftSize() + rightSize() + 1;
    }

    public Node<T> right() {
        return right;
    }

    public void right(Node<T> right) {
        this.right = right;
        this.height = Math.max(leftHeight(), rightHeight()) + 1;
        this.balance = rightHeight() - leftHeight();
        this.size = leftSize() + rightSize() + 1;
    }

    public int height() {
        return height;
    }

    public int leftHeight() {
        return Optional.ofNullable(left)
                .map(Node::height)
                .orElse(0);
    }

    public int rightHeight() {
        return Optional.ofNullable(right)
                .map(Node::height)
                .orElse(0);
    }

    public T element() {
        return element;
    }

    public int balance() {
        return balance;
    }

    public long size() {
        return size;
    }

    public long leftSize() {
        return Optional.ofNullable(left)
                .map(Node::size)
                .orElse(0L);
    }

    public long rightSize() {
        return Optional.ofNullable(right)
                .map(Node::size)
                .orElse(0L);
    }

    @Override
    public String toString() {
        return "Node{" +
                "element=" + element +
                ", balance=" + balance +
                ", size=" + size +
                ", height=" + height +
                '}';
    }
}
