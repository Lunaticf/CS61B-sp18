import static org.junit.Assert.*;

import org.junit.Test;



public class TestArrayDequeGold {
    @Test
    public void testDeque() {
        StudentArrayDeque<Integer> stdDeque = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> correctDeque = new ArrayDequeSolution<>();

        String message = "";

        // test 100 hundred times
        for (int i = 0; i < 100; i++) {
            int randomMethod = StdRandom.uniform(4);
            if (randomMethod == 0) {
                Integer temp = StdRandom.uniform(100);
                stdDeque.addFirst(temp);
                correctDeque.addFirst(temp);
                message += "addFirst(" + temp + ")\n";
            } else if (randomMethod == 1) {
                Integer temp = StdRandom.uniform(100);
                stdDeque.addLast(temp);
                correctDeque.addLast(temp);
                message += "addFirst(" + temp + ")\n";
            } else if (randomMethod == 2) {
                if (stdDeque.isEmpty()) {
                    continue;
                }
                message += "removeFirst()\n";
                assertEquals(message, correctDeque.removeFirst(), stdDeque.removeFirst());
            } else {
                if (stdDeque.isEmpty()) {
                    continue;
                }
                message += "removeLast()\n";
                assertEquals(message, correctDeque.removeLast(), stdDeque.removeLast());

            }

        }
    }
}
