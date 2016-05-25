import java.lang.String;
import java.util.*;
import java.io.*;

public class AIDummyController extends Controller
{
    private String[] ranks = {"2", "3", "4", "5", "6", "7", "9", "10", "Jack", "Queen", "King", "Ace"};
    private Random rng;
    private int declareCount;
    private int[] handSizes;
    private NeuralNetwork net;

    public AIDummyController(Player p)
    {
        super(p);
        rng = new Random();
        declareCount = 0;
        handSizes = new int[6];
        for (int i = 0; i < 6; i++)
            handSizes[i] = 8;
    }

    public void saveNeuralNetwork(String filename) {
        try {
            FileOutputStream f_out = new FileOutputStream(filename);
            try {
                ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
                obj_out.writeObject(net);
            }
            catch (IOException io) {}
        }
        catch (FileNotFoundException fnf) {}
    }

    public void loadNeuralNetwork(String filename) {
        try {
            FileInputStream f_in = new FileInputStream(filename);
            try {
                ObjectInputStream obj_in = new ObjectInputStream(f_in);
                try {
                    Object obj = obj_in.readObject();
                    if (obj instanceof NeuralNetwork) {
                        net = (NeuralNetwork) obj;
                    }
                }
                catch (ClassNotFoundException cnf) {}
            }
            catch (IOException io) {}
        }
        catch (FileNotFoundException fnf) {}
    }

    public void setUpNetwork() {
        // 52 x 6 grid --> vector
        // Which player owns each card?
        // Input vector additionally stores a card id, an old player id, and a new player id
        // Neural network can have columns of size 3+52x6 52 52x6
        // Read the output as the prediction for the new board. Probabilities!!!
        // We apply the neural network to our visualization after every move.
        // At the same time we "cheat" by training the network against real data.
        // The AI looks at the visualization, picks the last half suit that was asked
        // (or the one it has the most of) and asks for the highest probability card in the
        // half suit.
    }

    @Override
    public void hearQuestion(Question q)
    {
        declareCount++;
        if (q.worked())
        {
            handSizes[q.asker()]++;
            handSizes[q.target()]--;
        }
    }

    @Override
    public void hearDeclaration(Declaration d)
    {
        declareCount = 0;
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

        if ((!must) && declareCount < 50 + rng.nextInt(10)) // to keep things interesting
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

        int enemy = (super.player().id() + 1 + 2 * rng.nextInt(3)) % 6;
        while (handSizes[enemy] == 0)
            enemy = (enemy + 2 * (1 + rng.nextInt(1))) % 6;
        return new Question(super.player().id(), enemy, new Card(ranks[startRank], startSuit, startHighHalf));
    }
}
