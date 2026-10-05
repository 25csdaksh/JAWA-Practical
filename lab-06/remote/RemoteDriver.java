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

        // 1. Loop over Switchable[] toggling each using the default method
        System.out.println("\n--- [1. Toggling All Devices (Initial State: OFF -> ON)] ---");
        for (Switchable device : devices) {
            device.toggle(); // calls default toggle()
            System.out.println("Current status: " + device);
        }

        System.out.println("\n--- [2. Toggling All Devices Again (State: ON -> OFF)] ---");
        for (Switchable device : devices) {
            device.toggle();
            System.out.println("Current status: " + device);
        }

        // 2. Functional Interface implementation via Anonymous Class
        // Rule: Only allow lights at night (18:00 to 06:00), Fans always allowed
        DeviceScheduleRule nightOnlyAnonymousRule = new DeviceScheduleRule() {
            @Override
            public boolean canSwitchOn(Switchable device, int hour) {
                if (device instanceof Light) {
                    return (hour >= 18 || hour < 6); // night hours
                }
                return true; // fans permitted anytime
            }
        };

        // 3. Functional Interface implementation via Lambda Expression
        // Rule: Energy saving daytime rule (allow if hour between 8 and 22)
        DeviceScheduleRule ecoHourLambdaRule = (device, hour) -> (hour >= 8 && hour <= 22);

        System.out.println("\n--- [3. Evaluating Schedule Rules via Anonymous Class vs Lambda] ---");
        int testHour1 = 14; // 2:00 PM (Afternoon)
        int testHour2 = 21; // 9:00 PM (Night)

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
