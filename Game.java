import java.util.List;
import java.util.ArrayList;

// extensions to the Game class represent different modes of gameplay
// (they use different mixes of Controllers)
public abstract class Game
{
    private List<Player> players;
    private List<Controller> controllers;
    private int[] score;

    private boolean teamOut(int i)
    {
        return players.get(i % 2).out() &&
               players.get(i % 2 + 2).out() &&
               players.get(i % 2 + 4).out();
    }

    private int evaluateDeclaration(Declaration d)
    {
        if (d.noGuess())
            return 6; // code for not declaring

        int id = d.getQuestion(0).asker();
        d.setWork(true);
        for (int i = 0; i < 6; i++)
        {
            Question q = d.getQuestion(i);
            // not a card
            if (!q.card().isCard())
                return -1;
            // declares for the other team
            if (q.asker() % 2 != q.target() % 2)
                return -1;

            if (!players.get(q.target()).gotdem(q.card()))
                d.setWork(false);

            for (Player p : players)
            {
                if (p.gotdem(q.card()))
                {
                    p.lose(q.card());
                    break;
                }
            }
        }
        if (d.worked())
            score[id % 2]++;
        else
            score[(id + 1) % 2]++;

        if (teamOut(id))
            id = (id + 1) % 6;

        if (teamOut(id))
            return 7; // code for game over

        while (player.get(id).out())
            id = (id + 2) % 6;

        return id;
    }

    private int evaluateQuestion(Question q)
    {
        // not a remaining player
        if (players.get(q.target()).out())
            return -1;
        // not a card
        if (!q.card().isCard())
            return -1;
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
        for (Controller c : controllers)
            c.hearQuestion(q);

        return id;
    }

    public Game(List<Player> p, List<Controller> c)
    {
        players = p;
        controllers = c;
        score = new int[2];
        int id = 0, tmp;
        while (true)
        {
            tmp = evaluateDeclaration(controllers.get(id).declare(teamOut(id + 1)));
            while (tmp == -1)
            {
                System.out.println("Invalid Declaration!");
                tmp = evaluateDeclaration(controllers.get(id).declare(teamOut(id + 1)));
            }
            if (tmp != 6)
            {
                if (tmp == 7)
                    break;

                id = tmp;
                continue;
            }

            tmp = evaluateQuestion(controllers.get(id).ask());
            while (tmp == -1)
            {
                System.out.println("Invalid Question!");
                tmp = evaluateQuestion(controllers.get(id).ask());
            }
            id = tmp;
        }
        System.out.print("\nGood game!\nScore: " + score[0] + " to " + score[1] + ".");
    }
}
