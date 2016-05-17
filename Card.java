/**
 * Card.java
 *
 * <code>Card</code> represents a playing card.
 */
public class Card {

    /**
     * String value that holds the rank of the card
     */
    private String rank;

    /**
     * String value that holds the suit of the card
     */
    private String suit;

    /**
     * boolean value that holds the half-suit of the card
     */
    private boolean highHalf;


   /**
     * Creates a new <code>Card</code> instance.
     *
     * @param cardRank  a <code>String</code> value
     *                  containing the rank of the card
     * @param cardSuit  a <code>String</code> value
     *                  containing the suit of the card
     * @param cardHalfSuit a <code>boolean</code> value
     *                  containing the half-suit of the card
     */
    public Card(String cardRank, String cardSuit, boolean cardHighHalf) {
        //initializes a new Card with the given rank, suit, and half-suit
        rank = cardRank;
        suit = cardSuit;
        highHalf = cardHighHalf;
    }


    /**
     * Accesses this <code>Card's</code> rank.
     * @return this <code>Card's</code> rank.
     */
    public String rank() {
        return rank;
    }

    /**
     * Accesses this <code>Card's</code> suit.
     * @return this <code>Card's</code> suit.
     */
    public String suit() {
        return suit;
    }

   /**
     * Accesses this <code>Card's</code> half-suit.
     * @return this <code>Card's</code> half-suit.
     */
    public boolean highHalf() {
        return highHalf;
    }

    public boolean isCard() {
        if (suit != “Clubs” && suit != “Diamonds” && suit != “Hearts” && suit != “Spades”)
            return false;

        if (((rank != “2” && rank != “3” && rank != “4” && rank != “5” &&
              rank != “6” && rank != “7”) || highHalf) &&
            ((rank != “9” && rank != “10” && rank != “Jack” && rank != “Queen” &&
              rank != “King” && rank != “Ace”) || !highHalf))
            return false;

        return true;
    }

    // key (ordered low to high, alphabetically):
    // low clubs        = 0
    // high clubs       = 1
    // low diamonds     = 2
    // high diamonds    = 3
    // low hearts       = 4
    // high hearts      = 5
    // low spades       = 6
    // high spades      = 7
    public int code() {
        int base = 0; // Clubs
        if (suit.equals("Diamonds"))
            base = 2;
        if (suit.equals("Hearts"))
            base = 4;
        if (suit.equals("Spades"))
            base = 6;
        if (highHalf)
            base++;
        return base;
    }


    /** Compare this card with the argument.
     * @param otherCard the other card to compare to this
     * @return true if the rank, suit, and half-suit of this card
     *              are equal to those of the argument;
     *         false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (!Card.class.isAssignableFrom(obj.getClass()))
            return false;

        final Card otherCard = (Card) obj;
        return otherCard.suit().equals(this.suit())
            && otherCard.rank().equals(this.rank())
            && otherCard.highHalf() == this.highHalf();
    }

    /**
     * Converts the rank and suit into a string in the format
     *     "[Rank] of [Suit]".
     * This provides a useful way of printing the contents
     * of a <code>Deck</code> in an easily readable format or performing
     * other similar functions.
     *
     * @return a <code>String</code> containing the rank and suit of the
     *         card.
     */
    @Override
    public String toString() {
        return rank + " of " + suit;
    }
}
