public class Fan implements Switchable {
    private final String location;
    private boolean on;

    public Fan(String location) {
        this.location = location;
        this.on = false;
    }

    @Override
    public void on() {
        this.on = true;
        System.out.println("[FAN] " + location + " fan turned ON.");
    }

    @Override
    public void off() {
        this.on = false;
        System.out.println("[FAN] " + location + " fan turned OFF.");
    }

    @Override
    public boolean isOn() {
        return on;
    }

    @Override
    public String toString() {
        return "Fan (" + location + ") [State=" + (on ? "ON" : "OFF") + "]";
    }
}
