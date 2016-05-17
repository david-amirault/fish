import java.util.List;
import java.util.ArrayList;

public class Player
{
    private List<Card> hand;
    // the player's cards, distributed by half-suit
    // key (ordered low to high, alphabetically):
    // low clubs        = 0
    // high clubs       = 1
    // low diamonds     = 2
    // high diamonds    = 3
    // low hearts       = 4
    // high hearts      = 5
    // low spades       = 6
    // high spades      = 7
    private int[] halfsuits;

    // id's range from 0 to 5
    // fish is played 3v3, evens v. odds
    private int id;

    public Player(int playerNo)
    {
        hand = new ArrayList<Card>();
        halfsuits = new int[8];
        id = playerNo;
    }

    public boolean gotdem(Card other)
    {
        return hand.contains(other);
    }

    public void acquire(Card other)
    {
        hand.add(other);
        halfsuits[other.code()]++;
    }

    public void lose(Card other)
    {
        hand.remove(other);
        halfsuits[other.code()]--;
    }

    // out of cards
    public boolean out()
    {
        return hand.size() == 0;
    }

    public int size()
    {
        return hand.size();
    }

    @Override
    public String toString()
    {
        String rtn = "size = " + size() + "\n";
        for (int k = size() - 1; k >= 0; k--)
        {
            rtn += hand.get(k);
            if (k != 0)
                rtn += ", ";
            if ((size() - k) % 2 == 0)
                rtn += "\n";
        }

        for (int i = 0; i < 8; i++)
            rtn += halfsuits[i] + "\n";

        return "\n" + rtn;
    }
}
