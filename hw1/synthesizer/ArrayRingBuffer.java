package synthesizer;
import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T>  {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        fillCount = 0;
        this.capacity = capacity;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        if (isFull()) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        fillCount++;
        last++;
        if (last >= capacity) {
            last = 0;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T res = rb[first];
        rb[first] = null;
        fillCount--;
        first++;
        if (first >= capacity) {
            first = 0;
        }

        return res;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Ring buffer underflow");
        }
        return rb[first];
    }

    private class ArbIterator implements Iterator<T> {
        private int ptr;
        private int count;
        ArbIterator() {
            ptr = first;
            count = 0;
        }
        public boolean hasNext() {
            return count < fillCount();
        }
        public T next() {
            T res = rb[ptr];
            ptr++;
            count++;
            if (ptr >= capacity()) {
                ptr = 0;
            }
            return res;
        }

    }

    @Override
    public Iterator<T> iterator() {
        return new ArbIterator();
    }
}
