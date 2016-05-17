import java.util.Scanner;

public class HumanTextController extends Controller
{
    private Scanner keyboard;
    private int[] score;

    public HumanTextController(Player p)
    {
        super(p);
        keyboard = new Scanner(System.in);
        score = new int[2];
    }

    @Override
    public void hearDeclaration(Declaration d)
    {
        String msg = "Player " + d.getQuestion(0).asker() + " declares!\n";

        msg += "Guess:\n";
        for (int i = 0; i < 6; i++)
            msg += "Player " + d.getQuestion(i).target() + " has the " + d.getQuestion(i).card() + ".\n";

        msg += "Truth:\n";
        for (int i = 0; i < 6; i++)
            msg += "Player " + d.getQuestion(i).holder() + " has the " + d.getQuestion(i).card() + ".\n";

        if (d.worked())
        {
            msg += "Correct!\n";
            score[d.getQuestion(0).asker() % 2]++;
        }
        else
        {
            msg += "Incorrect!\n";
            score[d.getQuestion(0).target() % 2]++;
        }
        msg += "Score: " + score[0] + " to " + score[1] + ".\n";
        System.out.println(msg);
    }

    @Override
    public void hearQuestion(Question q)
    {
        String msg = "Player " + q.asker() + " asks Player " + q.target() + " for the " + q.card() + ".\n";
        if (q.worked())
            msg += "Correct!\n";
        else
            msg += "Go fish!\n";
        System.out.println(msg);
    }

    @Override
    public Declaration declare(boolean must)
    {
        Declaration dec = new Declaration();
        System.out.print("\n" + super.player().toString());
        if (!must)
        {
            System.out.print("Declare (y/n)? ");
            if (!keyboard.next().equals("y"))
                return dec;
        }
        else
            System.out.println("You must declare!");

        for (int i = 0; i < 6; i++)
        {
            System.out.print("Enter teammate: ");
            int t = keyboard.nextInt();
            System.out.print("Enter card rank: ");
            String r = keyboard.next();
            System.out.print("Enter card suit: ");
            String s = keyboard.next();
            System.out.print("Upper half-suit (true/false)? ");
            boolean h = keyboard.nextBoolean();
            Card c = new Card(r, s, h);
            dec.addQuestion(new Question(super.player().id(), t, c));
        }
        return dec;
    }

    @Override
    public Question ask()
    {
        System.out.print("\n" + super.player().toString());
        System.out.print("Enter target player: ");
        int t = keyboard.nextInt();
        System.out.print("Enter card rank: ");
        String r = keyboard.next();
        System.out.print("Enter card suit: ");
        String s = keyboard.next();
        System.out.print("Upper half-suit (true/false)? ");
        boolean h = keyboard.nextBoolean();
        Card c = new Card(r, s, h);
        return new Question(super.player().id(), t, c);
    }
}
