/**
 * This is a class that tests the Deck class.
 */
public class DeckTester {

	/**
	 * The main method in this class checks the Deck operations for consistency.
	 *	@param args is not used.
	 */
	public static void main(String[] args) {
        String[] normalranks = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        String[] normalsuits = {"Clubs", "Diamonds", "Hearts", "Spades"};
        int[] normalvalues = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        String[] smallranks = {"A", "B", "C"};
        String[] smallsuits = {"Giraffes", "Lions"};
        int[] smallvalues = {2, 1, 6};
        String[] jacksonranks = {"A", "B", "C"};
        String[] jacksonsuits = {"Thriller", "Bad", "Dangerous"};
        int[] jacksonvalues = {5, 5, 5};
        Deck normal = new Deck(normalranks, normalsuits, normalvalues);
        Deck small = new Deck(smallranks, smallsuits, smallvalues);
        Deck jackson = new Deck(jacksonranks, jacksonsuits, jacksonvalues);
        System.out.println(normal.size());
        System.out.println(normal.deal());
        System.out.println(normal.deal());
        System.out.println(normal.deal());
        System.out.println(normal.deal());
        System.out.println(normal.deal());
        System.out.println(normal.deal());
        System.out.println(normal.deal());
        System.out.println(normal.isEmpty());
        System.out.println(normal.toString());
        System.out.print('\n');
        System.out.println(small.size());
        System.out.println(small.deal());
        System.out.println(small.deal());
        System.out.println(small.deal());
        System.out.println(small.deal());
        System.out.println(small.deal());
        System.out.println(small.deal());
        System.out.println(small.deal());
        System.out.println(small.isEmpty());
        System.out.println(small.toString());
        System.out.print('\n');
        System.out.println(jackson.size());
        System.out.println(jackson.deal());
        System.out.println(jackson.deal());
        System.out.println(jackson.deal());
        System.out.println(jackson.deal());
        System.out.println(jackson.deal());
        System.out.println(jackson.deal());
        System.out.println(jackson.deal());
        System.out.println(jackson.isEmpty());
        System.out.println(jackson.toString());
        System.out.print('\n');
	}
}
