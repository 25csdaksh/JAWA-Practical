import java.util.Scanner;
import model.*;
import service.*;
import util.*;
import static util.Validator.*; // Static import for Validator methods

public class MiniBank {
    enum MenuOption {
        OPEN_ACCOUNT,
        DEPOSIT,
        WITHDRAW,
        TRANSFER,
        VIEW_STATEMENT,
        VALIDATE_ACCOUNT,
        VERIFY_CREDENTIALS,
        WORKING_HOURS,
        EXIT
    }

    private static Account findAccount(Account[] accounts, int count, String accNo) {
        for (int i = 0; i < count; i++) {
            if (accounts[i].getAccountNumber().equalsIgnoreCase(accNo.trim())) {
                return accounts[i];
            }
        }
        return null;
    }

    public static void main(String[] args) {
        BankInfo header = new BankInfo("MiniBank India", "Gujarat University Campus");
        System.out.println(header);

        Account[] accounts = new Account[100];
        int accountCount = 0;

        // Pre-load accounts (Demonstrating model package & Premium marker interface)
        accounts[accountCount++] = new SavingsAccount("Daksh Soni", 25000, 1000);
        accounts[accountCount++] = new CurrentAccount("Prof. Sharma", 40000, 15000);
        accounts[accountCount++] = new FixedDepositAccount("Alice Smith", 100000);

        System.out.println("\n--- [PRACTICAL 7 DEMONSTRATION: ANNOTATIONS & REFLECTION VALIDATOR] ---");

        // 1. Valid Account Validation Test
        System.out.println("\n1. Testing AnnotationValidator on a Valid Account (AC0001):");
        String[] validErrors = AnnotationValidator.validate(accounts[0]);
        if (validErrors.length == 0) {
            System.out.println("   [SUCCESS] Account AC0001 is completely valid! (0 metadata errors found)");
        } else {
            System.out.println("   [FAIL] Unexpected validation errors: " + java.util.Arrays.toString(validErrors));
        }

        // 2. Invalid Account Validation Test (Negative Balance: -100)
        System.out.println("\n2. Testing AnnotationValidator on an Invalid Account with Negative Balance (-100):");
        Account invalidNegativeAccount = new SavingsAccount("Invalid Account User", -100, 500);
        String[] negativeErrors = AnnotationValidator.validate(invalidNegativeAccount);
        System.out.println("   Validation Result for Invalid Account (" + negativeErrors.length + " errors detected):");
        for (String err : negativeErrors) {
            System.out.println("    -> " + err);
        }

        // 3. Supplementary Test: @MaxLength constraint violation
        System.out.println("\n3. Testing Supplementary @MaxLength Annotation Violation (Owner name > 25 chars):");
        Account longNameAccount = new CurrentAccount("Dr. Dakshina Murthy Soni The Third of Charusat", 5000, 2000);
        String[] maxLengthErrors = AnnotationValidator.validate(longNameAccount);
        System.out.println("   Validation Result for Long Name Account (" + maxLengthErrors.length + " errors detected):");
        for (String err : maxLengthErrors) {
            System.out.println("    -> " + err);
        }

        System.out.println("\n--- [PRACTICAL 6 RECAP: INTERFACES, LAMBDAS & DEFAULT METHODS] ---");
        // Functional Interface WithdrawRule via Anonymous Class and Lambda
        WithdrawRule anonymousNightLimitRule = new WithdrawRule() {
            @Override
            public boolean allow(Account account, long amount) {
                return amount <= 20000 && account.canWithdraw(amount);
            }
        };
        WithdrawRule lambdaAtmLimitRule = (account, amount) -> (amount <= 50000 && account.canWithdraw(amount));

        System.out.println("WithdrawRule Evaluation for AC0001 (Balance Rs. 25,000):");
        System.out.println(" - Attempt Rs. 15,000 -> Anonymous Night Rule (Max 20k): " + anonymousNightLimitRule.allow(accounts[0], 15000));
        System.out.println(" - Attempt Rs. 25,000 -> Anonymous Night Rule (Max 20k): " + anonymousNightLimitRule.allow(accounts[0], 25000));
        System.out.println(" - Attempt Rs. 25,000 -> Lambda ATM Rule (Max 50k):      " + lambdaAtmLimitRule.allow(accounts[0], 25000));

        System.out.println("-----------------------------------------------------------\n");

        Scanner scanner = new Scanner(System.in);
        boolean keepRunning = true;

        while (keepRunning) {
            System.out.println("\n----------------- MINIBANK INTERACTIVE MENU -----------------");
            System.out.println("1. Open Account (Savings / Current / Fixed Deposit)");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer (Local)");
            System.out.println("5. View Official Account Statement");
            System.out.println("6. Validate Account Metadata via Reflection (Practical 7)");
            System.out.println("7. Verify Customer Credentials (Static Import Validator)");
            System.out.println("8. Check Bank Working Hours");
            System.out.println("9. Exit");
            System.out.println("------------------------------------------------------------");
            System.out.print("Please enter your choice (1-9): ");

            int choice = -1;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.nextLine();
            }

            MenuOption selectedOption = switch (choice) {
                case 1 -> MenuOption.OPEN_ACCOUNT;
                case 2 -> MenuOption.DEPOSIT;
                case 3 -> MenuOption.WITHDRAW;
                case 4 -> MenuOption.TRANSFER;
                case 5 -> MenuOption.VIEW_STATEMENT;
                case 6 -> MenuOption.VALIDATE_ACCOUNT;
                case 7 -> MenuOption.VERIFY_CREDENTIALS;
                case 8 -> MenuOption.WORKING_HOURS;
                case 9 -> MenuOption.EXIT;
                default -> null;
            };

            if (selectedOption == null) {
                System.out.println("\n[ERROR] Invalid menu choice. Please select a valid number between 1 and 9.");
                continue;
            }

            switch (selectedOption) {
                case OPEN_ACCOUNT -> {
                    if (accountCount >= accounts.length) {
                        System.out.println("\n[ERROR] Bank database is full.");
                        break;
                    }
                    System.out.println("\nSelect Account Type:");
                    System.out.println("1. Savings Account (4.0% Interest, Min Balance, Premium Status)");
                    System.out.println("2. Current Account (0.0% Interest, Overdraft Facility)");
                    System.out.println("3. Fixed Deposit Account (7.0% Interest, Locked Deposit, Premium Status)");
                    System.out.print("Choice (1-3): ");
                    
                    int accTypeChoice = 1;
                    if (scanner.hasNextInt()) {
                        accTypeChoice = scanner.nextInt();
                        scanner.nextLine();
                    } else {
                        scanner.nextLine();
                    }

                    System.out.print("Enter owner's name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Enter opening balance (in Rs.): ");
                    long openingBal = scanner.nextLong();
                    scanner.nextLine();

                    Account newAcc;
                    switch (accTypeChoice) {
                        case 1 -> {
                            System.out.print("Enter minimum balance requirement (e.g. 500): ");
                            long minBal = scanner.nextLong();
                            scanner.nextLine();
                            newAcc = new SavingsAccount(name, openingBal, minBal);
                        }
                        case 2 -> {
                            System.out.print("Enter overdraft limit (e.g. 10000): ");
                            long odLimit = scanner.nextLong();
                            scanner.nextLine();
                            newAcc = new CurrentAccount(name, openingBal, odLimit);
                        }
                        case 3 -> {
                            newAcc = new FixedDepositAccount(name, openingBal);
                        }
                        default -> {
                            System.out.println("Invalid type selected. Defaulting to Savings Account.");
                            newAcc = new SavingsAccount(name, openingBal);
                        }
                    }

                    // Validate newly created account through AnnotationValidator reflection
                    String[] validationErrors = AnnotationValidator.validate(newAcc);
                    if (validationErrors.length > 0) {
                        System.out.println("\n[METADATA WARNING] Account created with annotation warnings:");
                        for (String err : validationErrors) {
                            System.out.println(" -> " + err);
                        }
                    }

                    accounts[accountCount++] = newAcc;
                    System.out.println("\n[SUCCESS] Account successfully opened:\n" + newAcc);
                }
                case DEPOSIT -> {
                    System.out.print("Enter Account Number (e.g. AC0001): ");
                    String accNo = scanner.nextLine().trim();
                    Account acc = findAccount(accounts, accountCount, accNo);
                    if (acc != null) {
                        System.out.print("Enter amount to deposit: ");
                        long amount = scanner.nextLong();
                        scanner.nextLine();
                        acc.deposit(amount);
                        System.out.println("[SUCCESS] Updated details: " + acc);
                    } else {
                        System.out.println("\n[ERROR] Account not found!");
                    }
                }
                case WITHDRAW -> {
                    System.out.print("Enter Account Number (e.g. AC0001): ");
                    String accNo = scanner.nextLine().trim();
                    Account acc = findAccount(accounts, accountCount, accNo);
                    if (acc != null) {
                        System.out.print("Enter amount to withdraw: ");
                        long amount = scanner.nextLong();
                        scanner.nextLine();
                        
                        if (lambdaAtmLimitRule.allow(acc, amount)) {
                            if (acc.withdraw(amount)) {
                                System.out.println("[SUCCESS] Withdrawal completed. Updated details: " + acc);
                            }
                        } else {
                            System.out.println("[ERROR] Transaction rejected by WithdrawRule (Exceeds limit or insufficient funds).");
                        }
                    } else {
                        System.out.println("\n[ERROR] Account not found!");
                    }
                }
                case TRANSFER -> {
                    System.out.print("Enter Source Account Number (Sender): ");
                    String srcAcc = scanner.nextLine().trim();
                    System.out.print("Enter Destination Account Number (Receiver): ");
                    String destAcc = scanner.nextLine().trim();
                    
                    Account src = findAccount(accounts, accountCount, srcAcc);
                    Account dest = findAccount(accounts, accountCount, destAcc);
                    
                    if (src != null && dest != null) {
                        System.out.print("Enter amount to transfer: ");
                        long amount = scanner.nextLong();
                        scanner.nextLine();
                        Account.transfer(src, dest, amount);
                    } else {
                        System.out.println("\n[ERROR] One or both account numbers are invalid.");
                    }
                }
                case VIEW_STATEMENT -> {
                    System.out.print("Enter Account Number (e.g. AC0001): ");
                    String accNo = scanner.nextLine().trim();
                    Account acc = findAccount(accounts, accountCount, accNo);
                    if (acc != null) {
                        System.out.println("\n" + StatementFormatter.buildStatement(acc));
                        System.out.println(String.format("5-Year Compound Projection: Rs. %.2f", acc.projectedBalance(5)));
                    } else {
                        System.out.println("\n[ERROR] Account not found!");
                    }
                }
                case VALIDATE_ACCOUNT -> {
                    System.out.print("Enter Account Number to validate: ");
                    String accNo = scanner.nextLine().trim();
                    Account acc = findAccount(accounts, accountCount, accNo);
                    if (acc != null) {
                        System.out.println("\n--- Inspecting Metadata via AnnotationValidator ---");
                        String[] errors = AnnotationValidator.validate(acc);
                        if (errors.length == 0) {
                            System.out.println("[VALID] Account satisfies all metadata constraints (@Id, @Positive, @MaxLength)!");
                        } else {
                            System.out.println("[INVALID] Constraints violated (" + errors.length + "):");
                            for (String err : errors) {
                                System.out.println(" - " + err);
                            }
                        }
                    } else {
                        System.out.println("\n[ERROR] Account not found!");
                    }
                }
                case VERIFY_CREDENTIALS -> {
                    System.out.println("\n--- Credential Verification Form (via Static Imports) ---");
                    System.out.print("Enter Mobile Number: ");
                    String mob = scanner.nextLine().trim();
                    System.out.println(" -> Valid Mobile? " + isValidMobile(mob));

                    System.out.print("Enter Email Address: ");
                    String email = scanner.nextLine().trim();
                    System.out.println(" -> Valid Email? " + isValidEmail(email));

                    System.out.print("Enter PAN Card: ");
                    String pan = scanner.nextLine().trim();
                    System.out.println(" -> Valid PAN? " + isValidPan(pan));
                }
                case WORKING_HOURS -> {
                    System.out.println("""
                                      Bank Working Hours:
                                      - Monday to Friday: 09:30 AM - 04:00 PM
                                      - Saturday: 09:30 AM - 01:30 PM (Closed on 2nd and 4th Saturdays)
                                      - Sunday & Public Holidays: CLOSED""");
                }
                case EXIT -> {
                    System.out.println("Thank you for using MiniBank. Goodbye!");
                    keepRunning = false;
                }
            }
        }
        scanner.close();
    }
}
