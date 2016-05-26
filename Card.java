import java.util.*;

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

    public Card(String cardRank, String cardSuit) {
        //find half-suit given rank and suit
        rank = cardRank;
        suit = cardSuit;
        highHalf = true;
        try {
            if (Integer.parseInt(cardRank) < 8) {
                highHalf = false;
            }
            else {
                highHalf = true;
            }
        }
        catch (NumberFormatException nfe) {
            highHalf = true;
        }
    }

    public Card(int id) {
        //generate card based on ID (0-51)
        switch (id%4) {
            case 0:
                suit = "Clubs";
                break;
            case 1:
                suit = "Diamonds";
                break;
            case 2:
                suit = "Hearts";
                break;
            case 3:
                suit = "Spades";
                break;
            default:
                suit = "";
                break;
        }
        if (id%13 < 6) {
            highHalf = false;
            rank = ""+(id%13+2);
        }
        else if (id%13 < 9) {
            highHalf = true;
            rank = ""+(id%13+2);
        }
        else if (id%13 == 9) {
            highHalf = true;
            rank = "Jack";
        }
        else if (id%13 == 10) {
            highHalf = true;
            rank = "Queen";
        }
        else if (id%13 == 11) {
            highHalf = true;
            rank = "King";
        }
        else if (id%13 == 12) {
            highHalf = true;
            rank = "Ace";
        }
    }

    public int id() {
        int mod4 = 0;
        int mod13 = 0;
        switch (suit) {
            case "Clubs":
                mod4 = 0;
                break;
            case "Diamonds":
                mod4 = 1;
                break;
            case "Hearts":
                mod4 = 2;
                break;
            case "Spades":
                mod4 = 3;
                break;
            default:
                mod4 = 0;
                break;
        }
        try {
            mod13 = Integer.parseInt(rank)-2;
        }
        catch (NumberFormatException nfe) {
            switch (rank) {
                case "Jack":
                    mod13 = 9;
                    break;
                case "Queen":
                    mod13 = 10;
                    break;
                case "King":
                    mod13 = 11;
                    break;
                case "Ace":
                    mod13 = 12;
                    break;
                default:
                    mod13 = 0;
                    break;
            }
        }
        return (13*mod4 + 40*mod13)%52;
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
        if (!(suit.equals("Clubs") || suit.equals("Diamonds") || suit.equals("Hearts") || suit.equals("Spades")))
            return false;

        if (!(((rank.equals("2") || rank.equals("3") || rank.equals("4") ||
                rank.equals("5") || rank.equals("6") || rank.equals("7")) && (!highHalf)) ||
              ((rank.equals("9") || rank.equals("10") || rank.equals("Jack") ||
                rank.equals("Queen") || rank.equals("King") || rank.equals("Ace")) && highHalf)))
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
