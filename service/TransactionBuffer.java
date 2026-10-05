package service;

public class TransactionBuffer {
    private final Runnable[] buffer;
    private int count = 0;
    private int in = 0;
    private int out = 0;
    private final int capacity;

    public TransactionBuffer(int capacity) {
        this.capacity = capacity;
        this.buffer = new Runnable[capacity];
    }

    public synchronized void produce(Runnable task) throws InterruptedException {
        while (count == capacity) {
            System.out.println("   [QUEUE FULL] Transaction buffer full. Producer thread waiting...");
            wait();
        }
        buffer[in] = task;
        in = (in + 1) % capacity;
        count++;
        System.out.println(String.format("   [QUEUED] Transaction task added to buffer. (Pending: %d/%d)", count, capacity));
        notifyAll();
    }

    public synchronized Runnable consume() throws InterruptedException {
        while (count == 0) {
            System.out.println("   [QUEUE EMPTY] Transaction buffer empty. Worker thread waiting...");
            wait();
        }
        Runnable task = buffer[out];
        out = (out + 1) % capacity;
        count--;
        System.out.println(String.format("   [DISPATCHED] Transaction task pulled for processing. (Pending: %d/%d)", count, capacity));
        notifyAll();
        return task;
    }

    public synchronized int getCount() {
        return count;
    }
}
