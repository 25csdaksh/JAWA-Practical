package producerconsumer;

import java.util.ArrayList;
import java.util.List;

public class ProducerConsumerDriver {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=================================================");
        System.out.println("  PRACTICAL 10 - PART A2: PRODUCER-CONSUMER      ");
        System.out.println("=================================================");

        int bufferCapacity = 4;
        int itemsToProduce = 12;
        BoundedBuffer buffer = new BoundedBuffer(bufferCapacity);
        List<Integer> consumedItems = new ArrayList<>();

        System.out.println(String.format("Buffer Capacity: %d | Items to Produce & Consume: %d\n", bufferCapacity, itemsToProduce));

        // 1. Producer Thread
        Thread producer = new Thread(() -> {
            for (int i = 1; i <= itemsToProduce; i++) {
                try {
                    buffer.produce(i);
                    Thread.sleep(30); // Simulate production time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("[PRODUCER FINISHED] All items produced.");
        }, "Producer-Thread");

        // 2. Consumer Thread
        Thread consumer = new Thread(() -> {
            for (int i = 1; i <= itemsToProduce; i++) {
                try {
                    int val = buffer.consume();
                    consumedItems.add(val);
                    Thread.sleep(60); // Consumer slightly slower to test buffer full waits
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("[CONSUMER FINISHED] All items consumed.");
        }, "Consumer-Thread");

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("\n-------------------------------------------------");
        System.out.println("VERIFICATION SUMMARY:");
        System.out.println("Consumed Items List: " + consumedItems);
        System.out.println("Total Items Consumed: " + consumedItems.size() + " / " + itemsToProduce);

        boolean inOrder = true;
        for (int i = 0; i < itemsToProduce; i++) {
            if (consumedItems.get(i) != (i + 1)) {
                inOrder = false;
                break;
            }
        }
        System.out.println("All items consumed in exact FIFO sequence with 0 loss? " + inOrder);
        System.out.println("=================================================\n");
    }
}
