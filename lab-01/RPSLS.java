import java.util.Random;
import java.util.Scanner;

public class RPSLS {
    // (a) Define an enum Move with ROCK, PAPER, SCISSORS, LIZARD, SPOCK.
    enum Move {
        ROCK, PAPER, SCISSORS, LIZARD, SPOCK
    }

    // (b) Write winner(Move a, Move b) returning 1 if a beats b, -1 if b beats a, 0 for a tie 
    // — use a switch expression listing what each move beats.
    public static int winner(Move a, Move b) {
        if (a == b) {
            return 0; // Tie
        }
        
        // Return 1 if 'a' beats 'b', -1 if 'b' beats 'a'
        return switch (a) {
            // Scissors cuts Paper, Rock crushes Scissors, Rock crushes Lizard, Scissors decapitates Lizard,
            // Paper covers Rock, Paper disproves Spock, Lizard poisons Spock, Lizard eats Paper,
            // Spock smashes Scissors, Spock vaporizes Rock
            case ROCK -> (b == Move.SCISSORS || b == Move.LIZARD) ? 1 : -1;
            case PAPER -> (b == Move.ROCK || b == Move.SPOCK) ? 1 : -1;
            case SCISSORS -> (b == Move.PAPER || b == Move.LIZARD) ? 1 : -1;
            case LIZARD -> (b == Move.SPOCK || b == Move.PAPER) ? 1 : -1;
            case SPOCK -> (b == Move.SCISSORS || b == Move.ROCK) ? 1 : -1;
        };
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        Move[] moves = Move.values();

        int playerWins = 0;
        int computerWins = 0;

        System.out.println("================================================");
        System.out.println("   Rock-Paper-Scissors-Lizard-Spock (5 Rounds)  ");
        System.out.println("================================================");
        System.out.println("Rules:");
        System.out.println(" - Scissors cuts Paper & decapitates Lizard");
        System.out.println(" - Paper covers Rock & disproves Spock");
        System.out.println(" - Rock crushes Scissors & crushes Lizard");
        System.out.println(" - Lizard poisons Spock & eats Paper");
        System.out.println(" - Spock smashes Scissors & vaporizes Rock");
        System.out.println("------------------------------------------------");

        // (c) Play 5 rounds:
        for (int round = 1; round <= 5; round++) {
            System.out.println("\n--- Round " + round + " ---");
            
            // Pick a random computer Move
            Move computerMove = moves[random.nextInt(moves.length)];

            // Read the player's Move
            Move playerMove = null;
            while (playerMove == null) {
                System.out.print("Enter your move (ROCK, PAPER, SCISSORS, LIZARD, SPOCK): ");
                String input = scanner.next().trim().toUpperCase();
                try {
                    playerMove = Move.valueOf(input);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid move! Please try again.");
                }
            }

            System.out.println("Your Move: " + playerMove);
            System.out.println("Computer's Move: " + computerMove);

            // Call winner()
            int result = winner(playerMove, computerMove);
            
            if (result == 1) {
                System.out.println("Round result: You win!");
                playerWins++;
            } else if (result == -1) {
                System.out.println("Round result: Computer wins!");
                computerWins++;
            } else {
                System.out.println("Round result: It's a tie!");
            }
        }

        // (d) After 5 rounds, print the overall winner summary.
        System.out.println("\n================================================");
        System.out.println("                  GAME OVER                     ");
        System.out.println("================================================");
        System.out.println("Final Score - You: " + playerWins + " | Computer: " + computerWins);
        if (playerWins > computerWins) {
            System.out.println("You win " + playerWins + "–" + computerWins);
        } else if (computerWins > playerWins) {
            System.out.println("Computer wins " + computerWins + "–" + playerWins);
        } else {
            System.out.println("Overall Result: It's a tie game! " + playerWins + "–" + computerWins);
        }
        
        scanner.close();
    }
}
