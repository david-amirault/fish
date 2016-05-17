// contains all the information from when a player asks a question
public class Question
{
    private int asker;
    private int target;
    // holder = the id of the actual player who has the card
    // this only gets assigned a value during a Declaration
    private int holder;

    private Card card;

    private boolean worked;

    public Question(int a, int t, Card c)
    {
        asker = a;
        target = t;
        holder = -1;
        card = c;
        worked = false;
    }

    public int asker() { return asker; }
    public int target() { return target; }
    public int holder() { return holder; }
    public Card card() { return card; }
    public boolean worked() { return worked; }

    public void setHold(int h)
    {
        holder = h;
    }

    public void setWork(boolean w)
    {
        worked = w;
    }
}
