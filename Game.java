import java.util.List;
import java.util.ArrayList;

public class Game
{
    private static final boolean debugging = true;
    private List<Controller> controllers;

    private boolean teamOut(int i)
    {
        return controllers.get(i % 2).player().out() &&
               controllers.get(i % 2 + 2).player().out() &&
               controllers.get(i % 2 + 4).player().out();
    }

    private int evaluateDeclaration(Declaration d)
    {
        if (d.size() == 0)
            return 6; // code for not declaring

        if (d.size() != 6)
            return -1;

        d.setWork(true);
        // check validity of declaration
        for (int i = 0; i < 6; i++)
        {
            Question q = d.getQuestion(i);
            // not a card
            if (!q.card().isCard())
                return -1;
            // declares for the other team
            if (q.asker() % 2 != q.target() % 2)
                return -1;
            // not a half-suit
            if (i < 5 && q.card().code() != d.getQuestion(i + 1).card().code())
                return -1;

            if (!controllers.get(q.target()).player().gotdem(q.card()))
                d.setWork(false);

        }

        // remove cards
        for (int i = 0; i < 6; i++)
        {
            Question q = d.getQuestion(i);
            for (Controller c : controllers)
            {
                if (c.player().gotdem(q.card()))
                {
                    d.getQuestion(i).setHold(c.player().id());
                    c.player().lose(q.card());
                    break;
                }
            }
        }
        int[] hands = new int[6];
        for (int i = 0; i < 6; i++)
            hands[i] = controllers.get(i).player().size();

        for (Controller c : controllers)
            c.player().sethands(hands);

        int id = d.getQuestion(0).asker();
        if (d.worked())
        {
            for (Controller c : controllers)
                c.player().point(id);
        }
        else
        {
            for (Controller c : controllers)
                c.player().point(id + 1);
        }
        for (Controller c : controllers)
            c.hearDeclaration(d);

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
            int[] hands = new int[6];
            for (int i = 0; i < 6; i++)
                hands[i] = controllers.get(i).player().size();

            for (Controller c : controllers)
                c.player().sethands(hands);
        }
        List<Double> board = new ArrayList<Double>(52*6);
        for (int i = 0; i < 52; i++) {
            for (int j = 0; j < 6; j++) {
                if (controllers.get(j).player().gotdem(new Card(i))) board.add(1.0);
                else board.add(0.0);
            }
        }
        for (Controller c : controllers)
            c.hearQuestion(q, board);

        return id;
    }

    public Game(List<Controller> c)
    {
        controllers = c;
        int id = 0, tmp;
        while (true)
        {
            if (debugging)
            {
                System.out.println();
                for (Controller con : controllers)
                    System.out.print(con.player().toString());
            }
            tmp = evaluateDeclaration(controllers.get(id).declare(teamOut(id + 1)));
            while (tmp == -1 || (tmp == 6 && teamOut(id + 1)))
            {
                System.out.println("Invalid Declaration!");
                if (debugging)
                {
                    System.out.println();
                    for (Controller con : controllers)
                        System.out.print(con.player().toString());
                }
                tmp = evaluateDeclaration(controllers.get(id).declare(teamOut(id + 1)));
            }
            if (tmp != 6)
            {
                if (tmp == 7)
                    break;

                id = tmp;
                continue;
            }

            if (debugging)
            {
                System.out.println();
                for (Controller con : controllers)
                    System.out.print(con.player().toString());
            }
            tmp = evaluateQuestion(controllers.get(id).ask());
            while (tmp == -1)
            {
                System.out.println("Invalid Question!");
                if (debugging)
                {
                    System.out.println();
                    for (Controller con : controllers)
                        System.out.print(con.player().toString());
                }
                tmp = evaluateQuestion(controllers.get(id).ask());
            }
            id = tmp;
        }
        System.out.print("\nGood game!\nScore: " + controllers.get(0).player().score()[0] + " to " + controllers.get(0).player().score()[1] + ".");
    }
}
