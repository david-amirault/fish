// this is how the Game class interacts with both people and AI
public abstract class Controller
{
    public abstract void hearQuestion(Question q);
    public abstract void hearDeclaration(Declaration d);

    // if it's your turn and the other team has no cards, you must declare
    public abstract Declaration declare(boolean must);
    public abstract Question ask();
}
