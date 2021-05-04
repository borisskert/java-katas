package gettheloop;

import java.util.LinkedList;

/**
 * https://www.codewars.com/kata/52a89c2ea8ddc5547a000863
 */
public class LoopInspector {

    private LinkedList<Node> visited = new LinkedList<>();
    private LinkedList<Node> cycle = new LinkedList<>();

    public int loopSize(Node node) {
        Node currentNode = node;

        while (visitNode(currentNode)) {
            currentNode = currentNode.getNext();
        }

        return cycle.size();
    }

    private boolean visitNode(Node node) {
        if(!visited.contains(node)) {
            visited.add(node);
        } else if(!cycle.contains(node)) {
            cycle.add(node);
        } else {
            return false;
        }

        return true;
    }
}
