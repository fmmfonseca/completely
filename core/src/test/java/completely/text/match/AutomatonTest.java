package completely.text.match;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

public abstract class AutomatonTest<T extends Automaton>
{
    @Rule
    public ExpectedException exceptionRule;

    protected Automaton automaton;

    public AutomatonTest()
    {
        this.exceptionRule = ExpectedException.none();
    }
}
