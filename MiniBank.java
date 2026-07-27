import java.util.Scanner;

// 3. Define a record named BankInfo with two String fields, name and branch.
record BankInfo(String name, String branch) {
    @Override
    public String toString() {
        return "=================================================\n" +
               "           " + name.toUpperCase() + "\n" +
               "           Branch: " + branch + "\n" +
               "=================================================";
    }
}

// 2. Create a public class named MiniBank that contains the main method.
public class MiniBank {

    // 4. Define an enum named MenuOption with the constants OPEN_ACCOUNT, DEPOSIT, 
    // WITHDRAW, TRANSFER, WORKING_HOURS (supplementary), and EXIT.
    enum MenuOption {
        OPEN_ACCOUNT,
        DEPOSIT,
        WITHDRAW,
        TRANSFER,
        WORKING_HOURS, // Supplementary option
        EXIT
    }

    public static void main(String[] args) {
        // Instantiate and print BankInfo as the header
        BankInfo header = new BankInfo("MiniBank India", "Gujarat University Campus");
        System.out.println(header);

        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;

        while (keepRunning) {
            // 5. Display the numbered menu to the user.
            System.out.println("\n----------------- MAIN MENU -----------------");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer");
            System.out.println("5. Check Bank Working Hours");
            System.out.println("6. Exit");
            System.out.println("---------------------------------------------");
            System.out.print("Please enter your choice (1-6): ");

            // Read input and check for integer
            int choice = -1;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                // Clear the invalid input from buffer
                scanner.next();
            }

            // Map choice to MenuOption using a switch expression.
            // If the input is invalid, MenuOption is null.
            MenuOption selectedOption = switch (choice) {
                case 1 -> MenuOption.OPEN_ACCOUNT;
                case 2 -> MenuOption.DEPOSIT;
                case 3 -> MenuOption.WITHDRAW;
                case 4 -> MenuOption.TRANSFER;
                case 5 -> MenuOption.WORKING_HOURS;
                case 6 -> MenuOption.EXIT;
                default -> null; // Supplementary: invalid input handling
            };

            // Supplementary: Re-prompt user when invalid choice is entered
            if (selectedOption == null) {
                System.out.println("\n[ERROR] Invalid menu choice. Please select a valid number between 1 and 6.");
                continue;
            }

            // 6. Use a switch expression on the MenuOption to print the placeholder
            String outputMsg = switch (selectedOption) {
                case OPEN_ACCOUNT -> "Open Account — to be implemented in a later lab.";
                case DEPOSIT      -> "Deposit — to be implemented in a later lab.";
                case WITHDRAW     -> "Withdraw — to be implemented in a later lab.";
                case TRANSFER     -> "Transfer — to be implemented in a later lab.";
                case WORKING_HOURS -> """
                                      Bank Working Hours:
                                      - Monday to Friday: 09:30 AM - 04:00 PM
                                      - Saturday: 09:30 AM - 01:30 PM (Closed on 2nd and 4th Saturdays)
                                      - Sunday & Public Holidays: CLOSED""";
                case EXIT         -> "Thank you for using MiniBank. Goodbye!";
            };

            System.out.println("\n" + outputMsg);

            // Stop loop on exit choice
            if (selectedOption == MenuOption.EXIT) {
                keepRunning = false;
            }
        }

        scanner.close();
    }
}
