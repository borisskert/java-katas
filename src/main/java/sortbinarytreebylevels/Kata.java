package sortbinarytreebylevels;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * https://www.codewars.com/kata/52bef5e3588c56132c0003bc
 */
class Kata {
    public static List<Integer> treeByLevels(Node node) {
        if (node == null) {
            return List.of();
        }

        return breadthFirst(node);
    }

    private static List<Integer> breadthFirst(Node node) {
        Iterator<Node> breadthFirst = new BreadthFirst(node);

        List<Integer> values = new ArrayList<>();

        while (breadthFirst.hasNext()) {
            Node currentNode = breadthFirst.next();
            values.add(currentNode.value);
        }

        return values;
    }
}

/**
 * https://en.wikipedia.org/wiki/Breadth-first_search
 */
class BreadthFirst implements Iterator<Node> {
    private final Queue<Node> nextNodes = new LinkedList<>();

    BreadthFirst(Node rootNode) {
        this.nextNodes.add(rootNode);
    }

    @Override
    public boolean hasNext() {
        return !nextNodes.isEmpty();
    }

    @Override
    public Node next() {
        Node currentNode = this.nextNodes.remove();

        if (currentNode.left != null) {
            nextNodes.add(currentNode.left);
        }

        if (currentNode.right != null) {
            nextNodes.add(currentNode.right);
        }

        return currentNode;
    }
}
