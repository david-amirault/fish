import java.util.List;
import java.util.ArrayList;

public class PlayerTester
{
    public static void main(String[] args)
    {
        String[] ranks = {"2", "3", "4", "5", "6", "7", "9", "10", "Jack", "Queen", "King", "Ace"};
        String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
        Deck fishy = new Deck(ranks, suits);
        System.out.println(fishy.toString());

        List<Player> players = new ArrayList<Player>();
        for (int i = 0; i < 6; i++)
            players.add(new Player(i));

        for (int i = 0; i < 6; i++)
            System.out.println(players.get(i).toString());

        int round = 0;
        while (!fishy.isEmpty())
        {
            players.get(round).acquire(fishy.deal());
            round = (round + 1) % 6;
        }

        for (int i = 0; i < 6; i++)
            System.out.println(players.get(i).toString());
    }
}
