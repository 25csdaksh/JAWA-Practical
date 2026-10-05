public class Light implements Switchable {
    private final String room;
    private boolean on;

    public Light(String room) {
        this.room = room;
        this.on = false;
    }

    @Override
    public void on() {
        this.on = true;
        System.out.println("[LIGHT] " + room + " light switched ON.");
    }

    @Override
    public void off() {
        this.on = false;
        System.out.println("[LIGHT] " + room + " light switched OFF.");
    }

    @Override
    public boolean isOn() {
        return on;
    }

    @Override
    public String toString() {
        return "Light (" + room + ") [State=" + (on ? "ON" : "OFF") + "]";
    }
}
