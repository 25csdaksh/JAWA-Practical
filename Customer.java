public class Customer implements Cloneable {
    private String name;
    private String email;
    private String mobile;
    private final String customerId;
    private Address address; // Address field

    private static long customerCounter = 100;

    // (3) Public static nested class named Address
    public static class Address {
        private final String line;
        private final String city;
        private final String pincode;

        public Address(String line, String city, String pincode) {
            this.line = line;
            this.city = city;
            this.pincode = pincode;
        }

        public String getLine() { return line; }
        public String getCity() { return city; }
        public String getPincode() { return pincode; }

        @Override
        public String toString() {
            return line + ", " + city + " - " + pincode;
        }
    }

    private static String generateCustomerId() {
        customerCounter++;
        return "CUST" + customerCounter;
    }

    // Constructor with Address
    public Customer(String name, String email, String mobile, Address address) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
        this.customerId = generateCustomerId();
    }

    // Constructor without Address (chains with null Address)
    public Customer(String name, String email, String mobile) {
        this(name, email, mobile, null);
    }

    // Getters
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getMobile() { return mobile; }
    public String getCustomerId() { return customerId; }
    public Address getAddress() { return address; }

    public void setAddress(Address address) {
        this.address = address;
    }

    // (4) clone() method that returns a copy of the customer (deep copying Address)
    @Override
    public Customer clone() {
        try {
            Customer cloned = (Customer) super.clone();
            // Perform deep copy for Address reference
            if (this.address != null) {
                cloned.address = new Address(this.address.line, this.address.city, this.address.pincode);
            }
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Cloning failed", e);
        }
    }

    @Override
    public String toString() {
        return "Customer[ID=" + customerId + ", Name=" + name + ", Email=" + email 
               + ", Mobile=" + mobile + ", Address={" + (address != null ? address : "None") + "}]";
    }
}
