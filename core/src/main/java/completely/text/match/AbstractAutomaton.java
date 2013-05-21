package completely.text.match;

/**
 * Skeletal implementation of the {@link Automaton} interface.
 */
public abstract class AbstractAutomaton implements Automaton
{
    protected final String pattern;

    public AbstractAutomaton(String pattern)
    {
        this.pattern = pattern;
    }

    @Override
    public String getPattern()
    {
        return pattern;
    }

    @Override
    public Automaton step(String symbols)
    {
        if (symbols.length() <= 0)
        {
            return this;
        }
        else
        {
            return step(symbols.charAt(0)).step(symbols.substring(1));
        }
    }
}
