import java.lang.String;
import java.util.Random;

public class AIDummyController extends Controller
{
    private String[] ranks = {"2", "3", "4", "5", "6", "7", "9", "10", "Jack", "Queen", "King", "Ace"};
    private Random rng;
    private int[] handSizes;

    public AIDummyController(Player p)
    {
        super(p);
        rng = new Random();
        handSizes = new int[6];
        for (int i = 0; i < 6; i++)
            handSizes[i] = 8;
    }

    @Override
    public void hearQuestion(Question q)
    {
        if (q.worked())
        {
            handSizes[q.asker()]++;
            handSizes[q.target()]--;
        }
    }

    @Override
    public void hearDeclaration(Declaration d)
    {
        for (int i = 0; i < 6; i++)
            handSizes[d.getQuestion(i).holder()]--;
    }

    @Override
    public Declaration declare(boolean must)
    {
        Declaration dec = new Declaration();
        int[] halfsuits = super.player().halfsuits();
        int biggest = 0, loc = 0;
        for (int i = 0; i < 8; i++)
        {
            if (halfsuits[i] > biggest)
            {
                biggest = halfsuits[i];
                loc = i;
            }
        }
        if (biggest == 6)
        {
            for (Card c : super.player().hand())
                if (c.code() == loc)
                    dec.addQuestion(new Question(super.player().id(), super.player().id(), c));

            return dec;
        }

        if (!must)
            return dec;

        int startRank = (loc % 2) * 6;
        String startSuit = "";
        boolean startHighHalf = false;
        for (Card c : super.player().hand())
        {
            if (c.code() == loc)
            {
                startSuit = c.suit();
                startHighHalf = c.highHalf();
                dec.addQuestion(new Question(super.player().id(), super.player().id(), c));
            }
        }
        boolean shifted = false;
        int teammate = (super.player().id() + 2) % 6;
        for (int i = 0; i < 6 - biggest; i++)
        {
            if (!shifted && i == handSizes[teammate])
                teammate = (teammate + 2) % 6;

            while (super.player().gotdem(new Card(ranks[startRank], startSuit, startHighHalf)))
                startRank++;

            dec.addQuestion(new Question(super.player().id(), teammate, new Card(ranks[startRank], startSuit, startHighHalf)));
            startRank++;
        }
        return dec;
    }

    @Override
    public Question ask()
    {
        int[] halfsuits = super.player().halfsuits();
        int biggest = 0, loc = 0;
        for (int i = 0; i < 8; i++)
        {
            if (halfsuits[i] > biggest)
            {
                biggest = halfsuits[i];
                loc = i;
            }
        }
        int startRank = (loc % 2) * 6;
        String startSuit = "";
        boolean startHighHalf = false;
        for (Card c : super.player().hand())
        {
            if (c.code() == loc)
            {
                startSuit = c.suit();
                startHighHalf = c.highHalf();
                break;
            }
        }
        while (super.player().gotdem(new Card(ranks[startRank], startSuit, startHighHalf)))
            startRank++;

        return new Question(super.player().id(), (super.player().id() + 1 + 2 * rng.nextInt(3)) % 6, new Card(ranks[startRank], startSuit, startHighHalf));
    }
}
