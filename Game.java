import java.util.List;
import java.util.ArrayList;

// extensions to the Game class represent different modes of gameplay
// (they use different mixes of Controllers)
public abstract class Game
{
    private List<Player> players;
    private List<Controller> controllers;

    private int evaluateQuestion(Question q)
    {
        // asks same team
        if (q.asker() % 2 == q.target() % 2)
            return -1;
        // not in this half-suit
        if (players.get(q.asker()).halfsuits()[q.card().code()] == 0)
            return -1;
        int id = q.target();
        // they actually have it
        if (players.get(q.target()).gotdem(q.card()))
        {
            q.setWork(true);
            id = q.asker();
            players.get(q.target()).lose(q.card());
            players.get(q.asker()).acquire(q.card());
        }
        // tell the Controllers what happened
        for (Controller c : controllers)
            c.hearQuestion(q);

        return id;
    }

    private int evaluateDeclaration(Declaration d)
    {
    }

    public Game(List<Player> p, List<Controller> c)
    {
        players = p;
        controllers = c;
    }
}
