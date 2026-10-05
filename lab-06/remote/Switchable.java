public interface Switchable {
    void on();
    void off();
    boolean isOn();

    // Default method to toggle the device state
    default void toggle() {
        if (isOn()) {
            off();
        } else {
            on();
        }
    }
}
