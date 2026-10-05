package util;

import model.Command;
import model.TransactionType;

public class CommandParser {
    public static Command parse(String line) {
        if (line == null || line.trim().isEmpty()) {
            throw new IllegalArgumentException("Empty command line");
        }

        String[] tokens = line.trim().split("\\s+");
        if (tokens.length != 3) {
            throw new IllegalArgumentException("Invalid command format. Expected 3 tokens: <TYPE> <ACC_NO> <AMOUNT>, but got " + tokens.length);
        }

        TransactionType type;
        try {
            type = TransactionType.valueOf(tokens[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown transaction type: " + tokens[0]);
        }

        String accountNumber = tokens[1];
        long amount;
        try {
            amount = Long.parseLong(tokens[2]);
            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be positive: " + amount);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount format: " + tokens[2]);
        }

        return new Command(type, accountNumber, amount);
    }
}
