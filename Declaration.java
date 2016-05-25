import java.util.List;
import java.util.ArrayList;

// contains all the information from when a player declares a half-suit
public class Declaration
{
    // one Question for each of the six cards in the half-suit
    private List<Question> guess;

    // this is still better than making boolean a public variable
    // (for encapsulation reasons)
    private boolean worked;

    public Declaration()
    {
        guess = new ArrayList<Question>();
        worked = false;
    }

    public boolean worked() { return worked; }

    public void setWork(boolean w)
    {
        worked = w;
    }

    public int size()
    {
        return guess.size();
    }

    public void addQuestion(Question q)
    {
        guess.add(q);
    }

    // note: 0 <= i < 6
    public Question getQuestion(int i)
    {
        return guess.get(i);
    }

    @Override
    public String toString()
    {
        String msg = "Player " + guess.get(0).asker() + " declares!\n";

        msg += "Guess:\n";
        for (int i = 0; i < 6; i++)
            msg += "Player " + guess.get(i).target() + " has the " + guess.get(i).card() + ".\n";

        msg += "Truth:\n";
        for (int i = 0; i < 6; i++)
            msg += "Player " + guess.get(i).holder() + " has the " + guess.get(i).card() + ".\n";

        if (worked)
            msg += "Correct!\n";
        else
            msg += "Incorrect!\n";

        return msg;
    }
}
