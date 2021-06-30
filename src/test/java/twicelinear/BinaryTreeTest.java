package twicelinear;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class BinaryTreeTest {

    BinaryTree<Integer> tree;

    @BeforeEach
    public void setup() throws Exception {
        tree = new BinaryTree<>();
    }

    @Test
    public void shouldCreateEmptyTree() throws Exception {
        assertThat(tree.size()).isEqualTo(0);
        assertThat(tree.contains(1)).isEqualTo(false);

        assertThat(tree.breadthFirst().hasNext()).isEqualTo(false);
    }

    @Test
    public void shouldAddElement() throws Exception {
        tree.add(1);
        assertThat(tree.size()).isEqualTo(1);
        assertThat(tree.get(0)).isEqualTo(1);
        assertThat(tree.contains(1)).isEqualTo(true);
        assertThatIteratorHasElements(tree.breadthFirst(), List.of(1));
    }

    <T> void assertThatIteratorHasElements(Iterator<T> iterator, List<T> elements) {
        List<T> actualElements = new ArrayList<>();
        while (iterator.hasNext()) {
            actualElements.add(iterator.next());
        }

        assertThat(actualElements).isEqualTo(elements);
    }

    @Test
    public void shouldAddTwoElements() throws Exception {
        tree.add(1);
        tree.add(2);

        assertThat(tree.size()).isEqualTo(2);
        assertThat(tree.get(0)).isEqualTo(1);
        assertThat(tree.get(1)).isEqualTo(2);
        assertThat(tree.contains(1)).isEqualTo(true);
        assertThat(tree.contains(2)).isEqualTo(true);
        assertThatIteratorHasElements(tree.breadthFirst(), List.of(1, 2));
    }

    @Test
    public void shouldAddThreeElementsWithoutNeedToBalance() throws Exception {
        tree.add(2);
        tree.add(1);
        tree.add(3);

        assertThat(tree.size()).isEqualTo(3);
        assertThat(tree.get(0)).isEqualTo(1);
        assertThat(tree.get(1)).isEqualTo(2);
        assertThat(tree.get(2)).isEqualTo(3);
        assertThat(tree.contains(1)).isEqualTo(true);
        assertThat(tree.contains(2)).isEqualTo(true);
        assertThat(tree.contains(3)).isEqualTo(true);
        assertThatIteratorHasElements(tree.breadthFirst(), List.of(2, 1, 3));
    }

    @Test
    public void shouldAddThreeElementsWithBalancingLeftSide() throws Exception {
        tree.add(3);
        tree.add(2);
        tree.add(1);

        assertThat(tree.size()).isEqualTo(3);
        assertThat(tree.get(0)).isEqualTo(1);
        assertThat(tree.get(1)).isEqualTo(2);
        assertThat(tree.get(2)).isEqualTo(3);
        assertThat(tree.contains(1)).isEqualTo(true);
        assertThat(tree.contains(2)).isEqualTo(true);
        assertThat(tree.contains(3)).isEqualTo(true);
        assertThatIteratorHasElements(tree.breadthFirst(), List.of(2, 1, 3));
    }

    @Test
    public void shouldAddThreeElementsWithBalancingRightSide() throws Exception {
        tree.add(1);
        tree.add(2);
        tree.add(3);

        assertThat(tree.size()).isEqualTo(3);
        assertThat(tree.get(0)).isEqualTo(1);
        assertThat(tree.get(1)).isEqualTo(2);
        assertThat(tree.get(2)).isEqualTo(3);
        assertThat(tree.contains(1)).isEqualTo(true);
        assertThat(tree.contains(2)).isEqualTo(true);
        assertThat(tree.contains(3)).isEqualTo(true);
        assertThatIteratorHasElements(tree.breadthFirst(), List.of(2, 1, 3));
    }

    @Test
    public void shouldNotAddExistingElementTwice() throws Exception {
        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.add(3);

        assertThat(tree.size()).isEqualTo(3);
        assertThat(tree.get(0)).isEqualTo(1);
        assertThat(tree.get(1)).isEqualTo(2);
        assertThat(tree.get(2)).isEqualTo(3);
        assertThat(tree.contains(1)).isEqualTo(true);
        assertThat(tree.contains(2)).isEqualTo(true);
        assertThat(tree.contains(3)).isEqualTo(true);
        assertThatIteratorHasElements(tree.breadthFirst(), List.of(2, 1, 3));
    }

    @Test
    public void shouldAddFourRandomElements() throws Exception {
        tree.add(3);
        tree.add(2);
        tree.add(12);
        tree.add(7);

        assertThat(tree.size()).isEqualTo(4);
        assertThat(tree.get(0)).isEqualTo(2);
        assertThat(tree.get(1)).isEqualTo(3);
        assertThat(tree.get(2)).isEqualTo(7);
        assertThat(tree.get(3)).isEqualTo(12);
        assertThat(tree.contains(1)).isEqualTo(false);
        assertThat(tree.contains(2)).isEqualTo(true);
        assertThat(tree.contains(3)).isEqualTo(true);
        assertThat(tree.contains(4)).isEqualTo(false);
        assertThat(tree.contains(5)).isEqualTo(false);
        assertThat(tree.contains(6)).isEqualTo(false);
        assertThat(tree.contains(7)).isEqualTo(true);
        assertThat(tree.contains(8)).isEqualTo(false);
        assertThat(tree.contains(9)).isEqualTo(false);
        assertThat(tree.contains(10)).isEqualTo(false);
        assertThat(tree.contains(11)).isEqualTo(false);
        assertThat(tree.contains(12)).isEqualTo(true);
        assertThatIteratorHasElements(tree.breadthFirst(), List.of(3, 2, 12, 7));
    }

    @Test
    public void shouldAddFiveRandomElements() throws Exception {
        tree.add(3);
        tree.add(2);
        tree.add(12);
        tree.add(7);
        tree.add(10);

        assertThat(tree.size()).isEqualTo(5);
        assertThat(tree.get(0)).isEqualTo(2);
        assertThat(tree.get(1)).isEqualTo(3);
        assertThat(tree.get(2)).isEqualTo(7);
        assertThat(tree.get(3)).isEqualTo(10);
        assertThat(tree.get(4)).isEqualTo(12);
        assertThat(tree.contains(1)).isEqualTo(false);
        assertThat(tree.contains(2)).isEqualTo(true);
        assertThat(tree.contains(3)).isEqualTo(true);
        assertThat(tree.contains(4)).isEqualTo(false);
        assertThat(tree.contains(5)).isEqualTo(false);
        assertThat(tree.contains(6)).isEqualTo(false);
        assertThat(tree.contains(7)).isEqualTo(true);
        assertThat(tree.contains(8)).isEqualTo(false);
        assertThat(tree.contains(9)).isEqualTo(false);
        assertThat(tree.contains(10)).isEqualTo(true);
        assertThat(tree.contains(11)).isEqualTo(false);
        assertThat(tree.contains(12)).isEqualTo(true);
        assertThatIteratorHasElements(tree.breadthFirst(), List.of(3, 2, 10, 7, 12));
    }

    @Test
    public void shouldAddSixRandomElements() throws Exception {
        tree.add(3);
        tree.add(2);
        tree.add(12);
        tree.add(7);
        tree.add(10);
        tree.add(5);

        assertThat(tree.size()).isEqualTo(6);
        assertThat(tree.get(0)).isEqualTo(2);
        assertThat(tree.get(1)).isEqualTo(3);
        assertThat(tree.get(2)).isEqualTo(5);
        assertThat(tree.get(3)).isEqualTo(7);
        assertThat(tree.get(4)).isEqualTo(10);
        assertThat(tree.get(5)).isEqualTo(12);
        assertThat(tree.contains(1)).isEqualTo(false);
        assertThat(tree.contains(2)).isEqualTo(true);
        assertThat(tree.contains(3)).isEqualTo(true);
        assertThat(tree.contains(4)).isEqualTo(false);
        assertThat(tree.contains(5)).isEqualTo(true);
        assertThat(tree.contains(6)).isEqualTo(false);
        assertThat(tree.contains(7)).isEqualTo(true);
        assertThat(tree.contains(8)).isEqualTo(false);
        assertThat(tree.contains(9)).isEqualTo(false);
        assertThat(tree.contains(10)).isEqualTo(true);
        assertThat(tree.contains(11)).isEqualTo(false);
        assertThat(tree.contains(12)).isEqualTo(true);
        assertThatIteratorHasElements(tree.breadthFirst(), List.of(7, 3, 10, 2, 5, 12));
    }

    @Test
    public void shouldAddSevenRandomElements() throws Exception {
        tree.add(3);
        tree.add(2);
        tree.add(12);
        tree.add(7);
        tree.add(10);
        tree.add(5);
        tree.add(15);

        assertThat(tree.size()).isEqualTo(7);
        assertThat(tree.get(0)).isEqualTo(2);
        assertThat(tree.get(1)).isEqualTo(3);
        assertThat(tree.get(2)).isEqualTo(5);
        assertThat(tree.get(3)).isEqualTo(7);
        assertThat(tree.get(4)).isEqualTo(10);
        assertThat(tree.get(5)).isEqualTo(12);
        assertThat(tree.get(6)).isEqualTo(15);
        assertThat(tree.contains(1)).isEqualTo(false);
        assertThat(tree.contains(2)).isEqualTo(true);
        assertThat(tree.contains(3)).isEqualTo(true);
        assertThat(tree.contains(4)).isEqualTo(false);
        assertThat(tree.contains(5)).isEqualTo(true);
        assertThat(tree.contains(6)).isEqualTo(false);
        assertThat(tree.contains(7)).isEqualTo(true);
        assertThat(tree.contains(8)).isEqualTo(false);
        assertThat(tree.contains(9)).isEqualTo(false);
        assertThat(tree.contains(10)).isEqualTo(true);
        assertThat(tree.contains(11)).isEqualTo(false);
        assertThat(tree.contains(12)).isEqualTo(true);
        assertThat(tree.contains(13)).isEqualTo(false);
        assertThat(tree.contains(14)).isEqualTo(false);
        assertThat(tree.contains(15)).isEqualTo(true);
        assertThatIteratorHasElements(tree.breadthFirst(), List.of(7, 3, 12, 2, 5, 10, 15));
    }

    @Test
    public void shouldAddTenRandomElements() throws Exception {
        tree.add(3);
        tree.add(2);
        tree.add(12);
        tree.add(7);
        tree.add(10);
        tree.add(5);
        tree.add(15);
        tree.add(8);
        tree.add(6);
        tree.add(9);

        assertThat(tree.size()).isEqualTo(10);
        assertThat(tree.get(0)).isEqualTo(2);
        assertThat(tree.get(1)).isEqualTo(3);
        assertThat(tree.get(2)).isEqualTo(5);
        assertThat(tree.get(3)).isEqualTo(6);
        assertThat(tree.get(4)).isEqualTo(7);
        assertThat(tree.get(5)).isEqualTo(8);
        assertThat(tree.get(6)).isEqualTo(9);
        assertThat(tree.get(7)).isEqualTo(10);
        assertThat(tree.get(8)).isEqualTo(12);
        assertThat(tree.get(9)).isEqualTo(15);
        assertThat(tree.contains(1)).isEqualTo(false);
        assertThat(tree.contains(2)).isEqualTo(true);
        assertThat(tree.contains(3)).isEqualTo(true);
        assertThat(tree.contains(4)).isEqualTo(false);
        assertThat(tree.contains(5)).isEqualTo(true);
        assertThat(tree.contains(6)).isEqualTo(true);
        assertThat(tree.contains(7)).isEqualTo(true);
        assertThat(tree.contains(8)).isEqualTo(true);
        assertThat(tree.contains(9)).isEqualTo(true);
        assertThat(tree.contains(10)).isEqualTo(true);
        assertThat(tree.contains(11)).isEqualTo(false);
        assertThat(tree.contains(12)).isEqualTo(true);
        assertThat(tree.contains(13)).isEqualTo(false);
        assertThat(tree.contains(14)).isEqualTo(false);
        assertThat(tree.contains(15)).isEqualTo(true);
        assertThatIteratorHasElements(tree.breadthFirst(), List.of(7, 3, 12, 2, 5, 9, 15, 6, 8, 10));
    }

    @Test
    public void shouldAddRandomNumbers() throws Exception {
        int count = 20;
        Random random = new Random(1295249578);

        for (int counter = 0; counter < count; counter++) {
            int randomNumber = random.nextInt((int)(count * 1.5));

            while (tree.contains(randomNumber)) {
                randomNumber = random.nextInt((int)(count * 1.5));
            }

            tree.add(randomNumber);

            assertThat(tree.contains(randomNumber)).isEqualTo(true);
            assertThat(tree.size()).isEqualTo(counter + 1);
        }
    }

    @Test
    @Disabled
    public void shouldFindFailingSetup() throws Exception {
        Random seed = new Random();

        int tries = 10;
        int count = 2000000;

        for(int tryCounter = 0; tryCounter < tries; tryCounter++) {
            BinaryTree<Integer> myTree = new BinaryTree<>();

            int randomSeed = seed.nextInt();
            Random random = new Random(randomSeed);

            for (int counter = 0; counter < count; counter++) {
                int randomNumber = random.nextInt((int)(count * 1.5));

                while (myTree.contains(randomNumber)) {
                    randomNumber = random.nextInt((int)(count * 1.5));
                }

                myTree.add(randomNumber);

                try {
                    assertThat(myTree.contains(randomNumber)).isEqualTo(true);
                    assertThat(myTree.size()).isEqualTo(counter + 1);
                } catch(AssertionError e) {
                    System.out.println("Failed! Random seed: " + randomSeed + ", counter: " + counter + ", number: " + randomNumber);
                    throw e;
                }
            }
        }
    }
}
