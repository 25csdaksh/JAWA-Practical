public class RemoteDriver {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("     PRACTICAL 6 - PART A1: REMOTE CONTROL       ");
        System.out.println("=================================================");

        Switchable[] devices = new Switchable[] {
            new Light("Living Room"),
            new Fan("Living Room"),
            new Light("Bed Room"),
            new Fan("Study")
        };

        System.out.println("\n--- [1. Toggling All Devices (Initial State: OFF -> ON)] ---");
        for (Switchable device : devices) {
            device.toggle();
            System.out.println("Current status: " + device);
        }

        System.out.println("\n--- [2. Toggling All Devices Again (State: ON -> OFF)] ---");
        for (Switchable device : devices) {
            device.toggle();
            System.out.println("Current status: " + device);
        }

        DeviceScheduleRule nightOnlyAnonymousRule = new DeviceScheduleRule() {
            @Override
            public boolean canSwitchOn(Switchable device, int hour) {
                if (device instanceof Light) {
                    return (hour >= 18 || hour < 6);
                }
                return true;
            }
        };

        DeviceScheduleRule ecoHourLambdaRule = (device, hour) -> (hour >= 8 && hour <= 22);

        System.out.println("\n--- [3. Evaluating Schedule Rules via Anonymous Class vs Lambda] ---");
        int testHour1 = 14;
        int testHour2 = 21;

        Switchable livingLight = devices[0];
        Switchable livingFan = devices[1];

        System.out.println(String.format("Hour %02d:00 | Light allowed (Anonymous Night Rule)? %b",
            testHour1, nightOnlyAnonymousRule.canSwitchOn(livingLight, testHour1)));
        System.out.println(String.format("Hour %02d:00 | Fan allowed (Anonymous Night Rule)?   %b",
            testHour1, nightOnlyAnonymousRule.canSwitchOn(livingFan, testHour1)));

        System.out.println(String.format("Hour %02d:00 | Light allowed (Anonymous Night Rule)? %b",
            testHour2, nightOnlyAnonymousRule.canSwitchOn(livingLight, testHour2)));

        System.out.println(String.format("Hour %02d:00 | Device allowed (Lambda Eco Rule)?      %b",
            testHour1, ecoHourLambdaRule.canSwitchOn(livingLight, testHour1)));
        System.out.println(String.format("Hour 03:00 | Device allowed (Lambda Eco Rule)?      %b",
            ecoHourLambdaRule.canSwitchOn(livingLight, 3)));

        System.out.println("=================================================\n");
    }
}
