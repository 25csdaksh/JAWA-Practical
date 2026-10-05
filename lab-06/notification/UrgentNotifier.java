public class UrgentNotifier implements Notifier, Urgent {
    private final String channelName;
    private final Notifier target;

    public UrgentNotifier(String channelName, Notifier target) {
        this.channelName = channelName;
        this.target = target;
    }

    @Override
    public void send(String message) {
        System.out.print("[URGENT - " + channelName + "] ");
        target.send(message);
    }
}
