public class ArrayDeque<T> {
    private int size;
    private int nextFirst;
    private int nextLast;
    private T[] items;

    private static final int MIN_INITIAL_CAPACITY = 8;

    /** "minus one" in circle */
    private int minusOne(int index) {
        index -= 1;
        if (index < 0) {
            index += items.length;
        }
        return index;
    }

    /** "plus one" in circle */
    private int plusOne(int index) {
        index += 1;
        if (index >= items.length) {
            index = index % items.length;
        }
        return index;
    }


    /** constructor */
    public ArrayDeque() {
        size = 0;
        items = (T[]) new Object[MIN_INITIAL_CAPACITY];
        nextFirst = items.length - 1;
        nextLast = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /** resize, core code */
    private void resize(int capacity) {
        T[] newItems = (T[]) new Object[capacity];
        System.arraycopy(items, nextFirst + 1, newItems, 0, items.length - nextFirst - 1);
        System.arraycopy(items, 0, newItems, items.length - nextFirst - 1, nextLast);
        items = newItems;
        // maintain the nextFirst and nextLast
        nextFirst = items.length - 1;
        nextLast = size;
    }

    public void addFirst(T item) {
        if (size == items.length - 1) {
            resize(items.length * 2);
        }

        items[nextFirst] = item;
        // maintain the nextFirst
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    public void addLast(T item) {
        if (size == items.length - 1) {
            resize(items.length * 2);
        }

        items[nextLast] = item;
        // maintain the nextLast
        nextLast = plusOne(nextLast);
        size += 1;
    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }

        int removedItemIndex = plusOne(nextFirst);
        T res = items[removedItemIndex];
        items[removedItemIndex] = null;
        nextFirst = removedItemIndex;
        size--;

        // check if need resize
        if (items.length >= 16 && size == items.length / 4) {
            resize(items.length / 2);
        }
        return res;
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }

        int removedItemIndex = minusOne(nextLast);
        T res = items[removedItemIndex];
        items[removedItemIndex] = null;
        nextLast = removedItemIndex;
        size--;

        // check if need resize
        if (items.length >= 16 && size == items.length / 4) {
            resize(items.length / 2);
        }

        return res;
    }

    public T get(int index) {
        int start = plusOne(nextFirst);
        for (int i = 0; i < index; i++) {
            start = plusOne(start);
        }
        return items[start];
    }

    public void printDeque() {
        int start = plusOne(nextFirst);
        for (int i = 0; i < size; i++) {
            System.out.print(items[start] + " ");
            start = plusOne(start);
        }
    }


}
