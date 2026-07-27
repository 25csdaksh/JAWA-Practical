public class Customer {
    private String name;
    private String email;
    private String mobile;
    private final String customerId;

    // Use a private static long field to keep count
    private static long customerCounter = 100; // Starts at 100 so next ID is CUST101

    // Private static method to generate unique customer IDs
    private static String generateCustomerId() {
        customerCounter++;
        return "CUST" + customerCounter;
    }

    // Constructor
    public Customer(String name, String email, String mobile) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.customerId = generateCustomerId();
    }

    // Public getter methods
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCustomerId() {
        return customerId;
    }

    @Override
    public String toString() {
        return "Customer[ID=" + customerId + ", Name=" + name + ", Email=" + email + ", Mobile=" + mobile + "]";
    }
}
