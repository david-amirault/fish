/**
 * This is a class that tests the Card class.
 */
public class CardTester {

	/**
	 * The main method in this class checks the Card operations for consistency.
	 *	@param args is not used.
	 */
	public static void main(String[] args) {
        Card heartless = new Card("Ace", "Hearts", 1);
        Card trump = new Card("Ace", "Spades", 1);
        Card taketwo = new Card("Ace", "Hearts", 1);
        System.out.println(heartless.suit());
        System.out.println(heartless.rank());
        System.out.println(heartless.pointValue());
        System.out.println(heartless.matches(trump));
        System.out.println(heartless.matches(taketwo));
        System.out.println(heartless.toString());
        System.out.print('\n');
        System.out.println(trump.suit());
        System.out.println(trump.rank());
        System.out.println(trump.pointValue());
        System.out.println(trump.matches(heartless));
        System.out.println(trump.matches(taketwo));
        System.out.println(trump.toString());
        System.out.print('\n');
        System.out.println(taketwo.suit());
        System.out.println(taketwo.rank());
        System.out.println(taketwo.pointValue());
        System.out.println(taketwo.matches(heartless));
        System.out.println(taketwo.matches(trump));
        System.out.println(taketwo.toString());
	}
}
