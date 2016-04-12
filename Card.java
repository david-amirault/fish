/**
 * Card.java
 *
 * <code>Card</code> represents a playing card.
 */
public class Card {

	/**
	 * String value that holds the suit of the card
	 */
	private String suit;

	/**
	 * String value that holds the rank of the card
	 */
	private String rank;

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
	 * Accesses this <code>Card's</code> suit.
	 * @return this <code>Card's</code> suit.
	 */
	public String suit() {
		return suit;
	}

	/**
	 * Accesses this <code>Card's</code> rank.
	 * @return this <code>Card's</code> rank.
	 */
	public String rank() {
		return rank;
	}

   /**
	 * Accesses this <code>Card's</code> half-suit.
	 * @return this <code>Card's</code> half-suit.
	 */
	public boolean highHalf() {
		return highHalf;
	}

	/** Compare this card with the argument.
	 * @param otherCard the other card to compare to this
	 * @return true if the rank, suit, and half-suit of this card
	 *              are equal to those of the argument;
	 *         false otherwise.
	 */
	public boolean matches(Card otherCard) {
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