import java.util.List;
import java.util.ArrayList;

public class Game
{
    private List<Controller> controllers;
    private int[] score;

    private boolean teamOut(int i)
    {
        return controllers.get(i % 2).player().out() &&
               controllers.get(i % 2 + 2).player().out() &&
               controllers.get(i % 2 + 4).player().out();
    }

    private int evaluateDeclaration(Declaration d)
    {
        if (d.noGuess())
            return 6; // code for not declaring

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

            if (!controllers.get(q.target()).player().gotdem(q.card()))
                d.setWork(false);

            for (Controller c : controllers)
            {
                if (c.player().gotdem(q.card()))
                {
                    c.player().lose(q.card());
                    break;
                }
            }
        }
        for (Controller c : controllers)
            c.hearDeclaration(d);

        int id = d.getQuestion(0).asker();
        if (d.worked())
            score[id % 2]++;
        else
            score[(id + 1) % 2]++;

        if (teamOut(id))
            id = (id + 1) % 6;

        if (teamOut(id))
            return 7; // code for game over

        while (controllers.get(id).player().out())
            id = (id + 2) % 6;

        return id;
    }

    private int evaluateQuestion(Question q)
    {
        // not a remaining player
        if (controllers.get(q.target()).player().out())
            return -1;
        // not a card
        if (!q.card().isCard())
            return -1;
        // asks same team
        if (q.asker() % 2 == q.target() % 2)
            return -1;
        // not in this half-suit
        if (controllers.get(q.asker()).player().halfsuits()[q.card().code()] == 0)
            return -1;

        int id = q.target();
        // they actually have it
        if (controllers.get(q.target()).player().gotdem(q.card()))
        {
            q.setWork(true);
            id = q.asker();
            controllers.get(q.target()).player().lose(q.card());
            controllers.get(q.asker()).player().acquire(q.card());
        }
        for (Controller c : controllers)
            c.hearQuestion(q);

        return id;
    }

    public Game(List<Controller> c)
    {
        controllers = c;
        score = new int[2];
        int id = 0, tmp;
        while (true)
        {
            try
            {
                Thread.sleep(3000);
            }
            catch (InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
            tmp = evaluateDeclaration(controllers.get(id).declare(teamOut(id + 1)));
            while (tmp == -1 || (tmp == 6 && teamOut(id + 1)))
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
