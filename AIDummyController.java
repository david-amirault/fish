import java.lang.String;
import java.util.*;
import java.io.*;

public class AIDummyController extends Controller
{
    private static final double certainty = 0.8;
    private String[] ranks = {"2", "3", "4", "5", "6", "7", "9", "10", "Jack", "Queen", "King", "Ace"};
    private Random rng;
    private int declareCount;
    private NeuralNetwork net;
    private List<Double> visualization;

    public AIDummyController(Player p)
    {
        super(p);
        rng = new Random();
        declareCount = 0;
        setUpNetwork();
    }

    public void saveNeuralNetwork(String filename) {
        try {
            FileOutputStream f_out = new FileOutputStream(filename);
            try {
                ObjectOutputStream obj_out = new ObjectOutputStream(f_out);
                obj_out.writeObject(net);
            }
            catch (IOException io) {} // catching exceptions is for communists
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
        List<Integer> sizes = new ArrayList<Integer>(2);
        sizes.add(52*6);
        sizes.add(52*6);
        int inputs = 52*6+12+52;
        net = new NeuralNetwork(inputs, sizes, 0.1);
        visualization = new ArrayList<Double>(52*6);
        for (int i = 0; i < 52*6; i++)
            visualization.add(new Double(0.2));

        whaddayaKnow(); // this will fix the extra 0.2
        saveNeuralNetwork("latest.nnt");
    }

    public void runNetwork(Question q, List<Double> board, boolean training) {
        List<Double> input = new ArrayList<Double>(52*6+12+52);
        for (Double d : visualization) input.add(d);
        for (int i = 0; i < 64; i++) {
            input.add(new Double(0.0));
        }
        input.set(52*6+q.asker(), 1.0);
        input.set(52*6+6+q.target(), 1.0);
        input.set(52*6+12+q.card().id(), 1.0);
        visualization = net.doOut(input);
        whaddayaKnow();
        if (training) {
            net.trainStep(input, board);
        }
    }

    public void whaddayaKnow() {
        // Clean up the board visualization by comfirming what is trivially known.
        for (int i = 0; i < 52; i++) {
            if (super.player().gotdem(new Card(i))) {
                for (int j = 0; j < 6; j++) {
                    visualization.set(6*i+j, 0.0);
                }
                visualization.set(6*i+super.player().id(), 1.0);
            }
            else {
                visualization.set(6*i+super.player().id(), 0.0);
            }
        }
    }

    @Override
    public void hearQuestion(Question q, List<Double> board)
    {
        loadNeuralNetwork("latest.nnt");
        runNetwork(q, board, true);
        saveNeuralNetwork("latest.nnt");
        if (!q.worked())
            declareCount++;
    }

    @Override
    public void hearDeclaration(Declaration d)
    {
        declareCount = 0;
    }

    @Override
    public Declaration declare(boolean must)
    {
        List<Declaration> decs = new ArrayList<Declaration>();
        double biggest = -1.0;
        int halfsuit = 0;
        for (int i = 0; i < 8; i++) // half-suit
        {
            Declaration dec = new Declaration();
            double prob = 1.0;
            int mod4 = i / 2;
            int mod13 = (i % 2) * 7;
            int cardid = (13 * mod4 + 40 * mod13) % 52;
            for (int j = 0; j < 6; j++) // card in half-suit
            {
                Card c = new Card(cardid);
                double chance = -1.0;
                int teammate = super.player().id();
                int likelyHolder = teammate;
                for (int k = 0; k < 3; k++) // teammate
                {
                    if (visualization.get(6 * cardid + teammate).doubleValue() > chance)
                    {
                        chance = visualization.get(6 * cardid + teammate).doubleValue();
                        likelyHolder = teammate;
                    }
                    teammate = (teammate + 2) % 6;
                }
                dec.addQuestion(new Question(super.player().id(), likelyHolder, c));
                prob *= chance;
                cardid = (cardid + 40) % 52;
            }
            if (prob > biggest)
            {
                biggest = prob;
                halfsuit = i;
            }
        }
        if (biggest > certainty || must || declareCount + rng.nextInt(10) > 50)
            return decs.get(halfsuit);

        return new Declaration();
    }

    @Override
    public Question ask()
    {
        int[] halfsuits = super.player().halfsuits();
        double biggest = -1.0;
        int enemy = (super.player().id() + 1) % 6;
        int playuh = enemy;
        Card winnuh = new Card(0);
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 52; j++)
            {
                Card c = new Card(j);
                if (halfsuits[c.code()] > 0 && visualization.get(6 * j + enemy).doubleValue() > biggest)
                {
                    biggest = visualization.get(6 * j + enemy).doubleValue();
                    playuh = enemy;
                    winnuh = c; // we have a winner!
                }
            }
            enemy = (enemy + 2) % 6;
        }
        System.out.println(new Question(super.player().id(), playuh, winnuh));
        return new Question(super.player().id(), playuh, winnuh);
    }
}
