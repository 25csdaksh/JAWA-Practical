package producerconsumer;

public class BoundedBuffer {
    private final int[] buffer;
    private int count = 0;
    private int in = 0;
    private int out = 0;
    private final int capacity;

    public BoundedBuffer(int capacity) {
        this.capacity = capacity;
        this.buffer = new int[capacity];
    }

    public synchronized void produce(int value) throws InterruptedException {
        // While buffer is full, producer must wait
        while (count == capacity) {
            System.out.println("   [BUFFER FULL] Producer waiting for consumer to free space...");
            wait();
        }

        buffer[in] = value;
        in = (in + 1) % capacity;
        count++;
        System.out.println(String.format(" -> [PRODUCED] Item %d added to buffer. (Buffer Size: %d/%d)", value, count, capacity));

        // Notify waiting consumer thread that an item is available
        notifyAll();
    }

    public synchronized int consume() throws InterruptedException {
        // While buffer is empty, consumer must wait
        while (count == 0) {
            System.out.println("   [BUFFER EMPTY] Consumer waiting for producer to add items...");
            wait();
        }

        int value = buffer[out];
        out = (out + 1) % capacity;
        count--;
        System.out.println(String.format(" <- [CONSUMED] Item %d retrieved from buffer. (Buffer Size: %d/%d)", value, count, capacity));

        // Notify waiting producer thread that a slot has become available
        notifyAll();
        return value;
    }
}
