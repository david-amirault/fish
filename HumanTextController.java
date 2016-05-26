import java.util.Scanner;

public class HumanTextController extends Controller
{
    private static final boolean debugging = true;
    private Scanner keyboard;

    public HumanTextController(Player p)
    {
        super(p);
        keyboard = new Scanner(System.in);
    }

    @Override
    public void hearDeclaration(Declaration d)
    {
        String msg = d.toString();
        msg += "Score: " + super.player().score()[0] + " to " + super.player().score()[1] + ".\n";
        System.out.println(msg);
        if (!debugging)
        {
            try
            {
                Thread.sleep(3000);
            }
            catch (InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void hearQuestion(Question q, List<Double> board)
    {
        System.out.println(q.toString());
        if (!debugging)
        {
            try
            {
                Thread.sleep(3000);
            }
            catch (InterruptedException ex)
            {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public Declaration declare(boolean must)
    {
        Declaration dec = new Declaration();
        if (!debugging)
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
            System.out.print("Enter teammate id: ");
            int t = keyboard.nextInt();
            System.out.print("Enter card rank: ");
            String r = keyboard.next();
            System.out.print("Enter card suit: ");
            String s = keyboard.next();
            Card c = new Card(r, s);
            dec.addQuestion(new Question(super.player().id(), t, c));
        }
        return dec;
    }

    @Override
    public Question ask()
    {
        if (!debugging)
            System.out.print("\n" + super.player().toString());

        System.out.print("Enter enemy id: ");
        int t = keyboard.nextInt();
        System.out.print("Enter card rank: ");
        String r = keyboard.next();
        System.out.print("Enter card suit: ");
        String s = keyboard.next();
        Card c = new Card(r, s);
        return new Question(super.player().id(), t, c);
    }
}
