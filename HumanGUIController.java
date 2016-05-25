public class HumanGUIController extends Controller
{
    private FishGUI gui;

    public HumanGUIController(Player p)
    {
        super(p);
        gui = new FishGUI(p);
        gui.displayGame();
    }

    @Override
    public void hearDeclaration(Declaration d)
    {
        gui.status(d.toString());
    }

    @Override
    public void hearQuestion(Question q)
    {
        gui.status(q.toString());
    }

    @Override
    public Declaration declare(boolean must)
    {
        Declaration dec = new Declaration();
        return dec;
    }

    @Override
    public Question ask()
    {
        Card c = new Card("Ace", "Spades");
        return new Question(super.player().id(), 1, c);
    }
}
