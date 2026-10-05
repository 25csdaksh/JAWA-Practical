package deadlock;

public class DeadlockDriver {
    static class Resource {
        private final int id;
        private final String name;

        public Resource(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() { return id; }
        public String getName() { return name; }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=================================================");
        System.out.println("   PRACTICAL 10 - PART A3: DEADLOCK REPRODUCTION ");
        System.out.println("=================================================");

        // 1. Reproducing Deadlock (Opposite Lock Ordering)
        System.out.println("--- [1. Reproducing Deadlock via Opposite Lock Acquisition Order] ---");
        Resource deadlockA = new Resource(1, "Deadlock-Resource-A");
        Resource deadlockB = new Resource(2, "Deadlock-Resource-B");

        Thread deadlockThread1 = new Thread(() -> {
            synchronized (deadlockA) {
                System.out.println("   [Thread 1] Locked " + deadlockA.getName() + ", waiting to lock " + deadlockB.getName() + "...");
                try { Thread.sleep(60); } catch (InterruptedException ignored) {}
                synchronized (deadlockB) {
                    System.out.println("   [Thread 1] Acquired both locks successfully!");
                }
            }
        }, "Deadlock-Worker-1");

        Thread deadlockThread2 = new Thread(() -> {
            synchronized (deadlockB) { // Opposite lock order (B before A)
                System.out.println("   [Thread 2] Locked " + deadlockB.getName() + ", waiting to lock " + deadlockA.getName() + "...");
                try { Thread.sleep(60); } catch (InterruptedException ignored) {}
                synchronized (deadlockA) {
                    System.out.println("   [Thread 2] Acquired both locks successfully!");
                }
            }
        }, "Deadlock-Worker-2");

        // Make deadlock threads daemons so JVM terminates cleanly after tests
        deadlockThread1.setDaemon(true);
        deadlockThread2.setDaemon(true);

        deadlockThread1.start();
        deadlockThread2.start();

        // Wait 300ms to detect the deadlock
        deadlockThread1.join(250);
        deadlockThread2.join(250);

        if (deadlockThread1.isAlive() && deadlockThread2.isAlive()) {
            System.out.println("   [DEADLOCK CONFIRMED!] Both threads are stuck in circular waiting state (BLOCKED on each other).");
            System.out.println("   -> Thread 1 State: " + deadlockThread1.getState() + " | Thread 2 State: " + deadlockThread2.getState());
        }

        // 2. Removing Deadlock (Consistent Global Lock Ordering)
        System.out.println("\n--- [2. Eliminating Deadlock with Consistent Lock Ordering (Lower ID First)] ---");
        Resource safeA = new Resource(1, "Safe-Resource-A");
        Resource safeB = new Resource(2, "Safe-Resource-B");

        Runnable safeTask1 = () -> performSafeOperation(safeA, safeB, "Safe-Thread-1");
        Runnable safeTask2 = () -> performSafeOperation(safeB, safeA, "Safe-Thread-2");

        Thread safe1 = new Thread(safeTask1, "Safe-Worker-1");
        Thread safe2 = new Thread(safeTask2, "Safe-Worker-2");

        safe1.start();
        safe2.start();

        safe1.join();
        safe2.join();

        System.out.println("\n[SUCCESS] Both threads finished normally with 0 deadlocks due to consistent lock ordering!");
        System.out.println("=================================================\n");
    }

    private static void performSafeOperation(Resource r1, Resource r2, String threadTag) {
        // Consistent global ordering: Always acquire the lock with the smaller ID first
        Resource firstLock = (r1.getId() < r2.getId()) ? r1 : r2;
        Resource secondLock = (firstLock == r1) ? r2 : r1;

        synchronized (firstLock) {
            System.out.println(String.format("   [%s] Acquired 1st lock on %s (ID: %d)", threadTag, firstLock.getName(), firstLock.getId()));
            try { Thread.sleep(30); } catch (InterruptedException ignored) {}
            synchronized (secondLock) {
                System.out.println(String.format("   [%s] Acquired 2nd lock on %s (ID: %d). Operation complete!", threadTag, secondLock.getName(), secondLock.getId()));
            }
        }
    }
}
