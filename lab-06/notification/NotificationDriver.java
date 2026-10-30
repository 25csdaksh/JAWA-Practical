public class NotificationDriver {
    static class UrgentSecurityAlert implements Notifier, Urgent {
        @Override
        public void send(String msg) {
            System.out.println("[URGENT SECURITY BROADCAST] *** " + msg.toUpperCase() + " ***");
        }
    }

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   PRACTICAL 6 - PART A2: NOTIFICATION SENDERS   ");
        System.out.println("=================================================");

        Notifier emailNotifier = msg -> System.out.println("[EMAIL] Dispatching email: \"" + msg + "\"");
        Notifier smsNotifier = msg -> System.out.println("[SMS] Sending text message: \"" + msg + "\"");
        Notifier pushNotifier = msg -> System.out.println("[PUSH] Sending Mobile App Push Alert: \"" + msg + "\"");

        Notifier urgentSmsNotifier = new UrgentNotifier("CRITICAL-SMS", msg -> System.out.println("[URGENT SMS] " + msg));
        Notifier urgentSecurityAlert = new UrgentSecurityAlert();

        Notifier[] notifiers = new Notifier[] {
            emailNotifier,
            smsNotifier,
            pushNotifier,
            urgentSmsNotifier,
            urgentSecurityAlert
        };

        String broadcastMessage = "System scheduled maintenance will begin at 02:00 AM IST.";

        System.out.println("\n--- Broadcasting Message to All Channels ---");
        System.out.println("Message: \"" + broadcastMessage + "\"\n");

        for (int i = 0; i < notifiers.length; i++) {
            Notifier notifier = notifiers[i];
            System.out.println("Channel #" + (i + 1) + ":");

            if (notifier instanceof Urgent) {
                System.out.println(" -> [MARKER DETECTED] Urgent channel! Sending duplicate notification for redundancy:");
                notifier.send(broadcastMessage);
                notifier.send(broadcastMessage);
            } else {
                notifier.send(broadcastMessage);
            }
            System.out.println();
        }

        System.out.println("=================================================\n");
    }
}
