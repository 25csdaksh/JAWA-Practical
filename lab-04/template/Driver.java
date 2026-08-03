public class Driver {
    public static void main(String[] args) {
        String template = "Dear {name}, order {id} ships {date}.";
        String[] names = {"name", "id"};
        String[] values = {"Riya", "A07"};

        System.out.println("=== TEMPLATE FILLER TEST ===");
        System.out.println("Template      : " + template);
        System.out.println("Placeholders  : " + java.util.Arrays.toString(names));
        System.out.println("Values        : " + java.util.Arrays.toString(values));

        String result = TemplateFiller.fill(template, names, values);
        System.out.println("Filled Result : " + result);

        // Additional testing with more placeholders and values
        String template2 = "Hello {user}, your balance for account {acc_num} is Rs. {bal}. Thank you, {user}!";
        String[] names2 = {"user", "acc_num", "bal"};
        String[] values2 = {"Daksh Soni", "AC0001", "1000"};
        System.out.println("\nTemplate 2    : " + template2);
        System.out.println("Filled Result 2: " + TemplateFiller.fill(template2, names2, values2));
    }
}
