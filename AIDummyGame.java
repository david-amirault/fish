import java.util.List;
import java.util.ArrayList;

public class AIDummyGame
{
    public static void main(String[] args)
    {
        String[] ranks = {"2", "3", "4", "5", "6", "7", "9", "10", "Jack", "Queen", "King", "Ace"};
        String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
        Deck fishy = new Deck(ranks, suits);

        List<Player> p = new ArrayList<Player>();
        for (int i = 0; i < 6; i++)
            p.add(new Player(i));

        int round = 0;
        while (!fishy.isEmpty())
        {
            p.get(round).acquire(fishy.deal());
            round = (round + 1) % 6;
        }

        List<Controller> c = new ArrayList<Controller>();
        for (int i = 0; i < 6; i++)
            c.add(new AIDummyController(p.get(i)));

        new Game(c);
    }
}
