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
        return gui.declare(must);
    }

    @Override
    public Question ask()
    {
        return gui.ask();
    }
}
