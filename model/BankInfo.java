package model;

public record BankInfo(String name, String branch) {
    @Override
    public String toString() {
        return "=================================================\n" +
               "           " + name.toUpperCase() + "\n" +
               "           Branch: " + branch + "\n" +
               "=================================================";
    }
}
