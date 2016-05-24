import java.util.List;
import java.util.ArrayList;

public class Player
{
    private List<Card> hand;
    // the player's cards, distributed by half-suit
    // indices: (ordered low to high, alphabetically)
    // low clubs        = 0
    // high clubs       = 1
    // low diamonds     = 2
    // high diamonds    = 3
    // low hearts       = 4
    // high hearts      = 5
    // low spades       = 6
    // high spades      = 7
    private int[] halfsuits;
    private int[] score;

    // id's range from 0 to 5
    // fish is played 3v3, evens v. odds
    private int id;

    public Player(int i)
    {
        hand = new ArrayList<Card>();
        halfsuits = new int[8];
        score = new int[2];
        id = i;
    }

    public List<Card> hand() { return hand; }
    public int size() { return hand.size(); }
    public int[] halfsuits() { return halfsuits; }
    public int[] score() { return score; }
    public int id() { return id; }

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

    public void point(int i)
    {
        score[i % 2]++;
    }

    // out of cards
    public boolean out()
    {
        return hand.size() == 0;
    }

    @Override
    public String toString()
    {
        String rtn = "Player " + id + "\n";
        for (int k = size() - 1; k >= 0; k--)
        {
            rtn += hand.get(k);
            if (k != 0)
                rtn += ", ";
            if ((size() - k) % 2 == 0 || k == 0)
                rtn += "\n";
        }
        return rtn;
    }
}
