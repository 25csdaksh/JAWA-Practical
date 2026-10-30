import java.util.Scanner;

public class VendingMachine {
    enum Coin {
        ONE, TWO, FIVE, TEN
    }

    public static void main(String[] args) {
        int snackPrice = 15;
        int total = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Vending Machine!");
        System.out.println("Snack Price: " + snackPrice + " Rs.");
        System.out.println("Insert coins (ONE, TWO, FIVE, TEN):");

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

        System.out.println("Paid. Change: " + (total - snackPrice));
        scanner.close();
    }
}
