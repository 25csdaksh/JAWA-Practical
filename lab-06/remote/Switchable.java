public interface Switchable {
    void on();
    void off();
    boolean isOn();

    default void toggle() {
        if (isOn()) {
            off();
        } else {
            on();
        }
    }
}
