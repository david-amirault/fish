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

    public boolean noGuess()
    {
        return guess.size() == 0;
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
}
