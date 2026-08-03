public class CommandParser {
    public static Command parse(String line) {
        if (line == null || line.trim().isEmpty()) {
            throw new IllegalArgumentException("Command line is empty.");
        }

        String[] parts = line.trim().split("\\s+");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid command format. Expected exactly 3 parts: <TYPE> <ACCOUNT_NUMBER> <AMOUNT>. Found: " + parts.length);
        }

        TransactionType type;
        try {
            type = TransactionType.valueOf(parts[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown transaction type: " + parts[0] + ". Valid types: DEPOSIT, WITHDRAW, TRANSFER");
        }

        String accountNumber = parts[1];

        long amount;
        try {
            amount = Long.parseLong(parts[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Amount must be a valid whole number: " + parts[2]);
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be a positive whole number: " + amount);
        }

        return new Command(type, accountNumber, amount);
    }
}
