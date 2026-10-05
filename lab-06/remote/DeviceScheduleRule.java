@FunctionalInterface
public interface DeviceScheduleRule {
    boolean canSwitchOn(Switchable device, int hour);
}
