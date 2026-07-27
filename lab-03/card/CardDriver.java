public class CardDriver {
    public static void main(String[] args) {
        Card[] hand = new Card[5];
        int cardCount = 0;

        Card[] cardStream = new Card[] {
            new Card("King", "Diamonds"),
            new Card("Ace", "Spades"),
            new Card("Queen", "Hearts"),
            new Card("Ace", "Spades"), // Duplicate!
            new Card("Ten", "Clubs")
        };

        System.out.println("Adding cards to collection:");
        for (Card nextCard : cardStream) {
            boolean isDuplicate = false;
            for (int i = 0; i < cardCount; i++) {
                if (hand[i].equals(nextCard)) {
                    isDuplicate = true;
                    break;
                }
            }

            if (isDuplicate) {
                System.out.println("[ALERT] Duplicate found: " + nextCard);
            } else {
                if (cardCount < hand.length) {
                    hand[cardCount++] = nextCard;
                    System.out.println("Added: " + nextCard);
                }
            }
        }
    }
}
