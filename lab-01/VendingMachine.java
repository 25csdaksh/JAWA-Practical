import java.util.Scanner;

public class VendingMachine {
    // (a) Define an enum Coin with constants ONE, TWO, FIVE, TEN.
    enum Coin {
        ONE, TWO, FIVE, TEN
    }

    public static void main(String[] args) {
        // (b) In main, set a snack price of 15 and a running total of 0; create a Scanner.
        int snackPrice = 15;
        int total = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Vending Machine!");
        System.out.println("Snack Price: " + snackPrice + " Rs.");
        System.out.println("Insert coins (ONE, TWO, FIVE, TEN):");

        // (c) Loop: read a coin name, use a switch expression to convert the Coin to its value, 
        // add it to the total, and print the total so far.
        // (d) Stop the loop once the total reaches 15 or more.
        while (total < snackPrice) {
            System.out.print("Enter coin: ");
            String input = scanner.next().trim().toUpperCase();
            try {
                Coin coin = Coin.valueOf(input);
                int value = switch (coin) {
                    case ONE -> 1;
                    case TWO -> 2;
                    case FIVE -> 5;
                    case TEN -> 10;
                };
                total += value;
                System.out.println("Added: " + value + ". Total so far: " + total);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid coin name! Enter ONE, TWO, FIVE, or TEN.");
            }
        }

        // (e) Print the change to return (total − 15).
        System.out.println("Paid. Change: " + (total - snackPrice));
        scanner.close();
    }
}
