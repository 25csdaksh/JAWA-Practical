import java.util.Objects;

public class Card {
    private String rank;
    private String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    // (b) Override toString() to return rank + “ of ” + suit
    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    // (c) Override equals() by rank + suit
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card card = (Card) obj;
        return Objects.equals(rank, card.rank) && Objects.equals(suit, card.suit);
    }

    // Override hashCode() by rank + suit
    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }
}
