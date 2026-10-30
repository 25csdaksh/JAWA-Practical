import java.util.Scanner;

public class Thermostat {
    private String location;
    private int temperature;
    private static final int MIN = 16;
    private static final int MAX = 30;
    private static int activeCount = 0;

    public Thermostat(String location, int startTemp) {
        this.location = location;
        if (startTemp >= MIN && startTemp <= MAX) {
            this.temperature = startTemp;
        } else {
            this.temperature = 22;
        }
        activeCount++;
    }

    public Thermostat(String location) {
        this(location, 22);
    }

    public void raise() {
        if (this.temperature < MAX) {
            this.temperature++;
        } else {
            System.out.println("Already at maximum (30)");
        }
    }

    public void lower() {
        if (this.temperature > MIN) {
            this.temperature--;
        } else {
            System.out.println("Already at minimum (16)");
        }
    }

    public int getTemperature() {
        return this.temperature;
    }

    public String getLocation() {
        return this.location;
    }

    public static int getActiveCount() {
        return activeCount;
    }

    public static void main(String[] args) {
        System.out.println("--- Smart Thermostat Test ---");

        Thermostat t1 = new Thermostat("Living Room", 25);
        Thermostat t2 = new Thermostat("Bed Room");

        System.out.println("Thermostat 1 (" + t1.getLocation() + ") starting temp: " + t1.getTemperature());
        System.out.println("Thermostat 2 (" + t2.getLocation() + ") starting temp: " + t2.getTemperature());

        System.out.println("\nRaising Thermostat 1 10 times:");
        for (int i = 1; i <= 10; i++) {
            t1.raise();
            System.out.println("After raise " + i + ": " + t1.getTemperature());
        }

        System.out.println("\nLowering Thermostat 1 20 times:");
        for (int i = 1; i <= 20; i++) {
            t1.lower();
            System.out.println("After lower " + i + ": " + t1.getTemperature());
        }

        System.out.println("\nActive Thermostats Count: " + Thermostat.getActiveCount());
    }
}
