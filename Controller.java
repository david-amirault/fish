// this is how the Game class interacts with both people and AI
import java.util.*;
public abstract class Controller
{
    private Player player;

    public Controller(Player p)
    {
        player = p;
    }

    public Player player() { return player; }

    public abstract void hearDeclaration(Declaration d);
    public abstract void hearQuestion(Question q, List<Double> board);

    // if it's your turn and the other team has no cards, you must declare
    public abstract Declaration declare(boolean must);
    public abstract Question ask();
}
