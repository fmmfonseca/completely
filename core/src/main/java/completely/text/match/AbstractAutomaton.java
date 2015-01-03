package completely.text.match;

import static completely.common.Precondition.checkPointer;

/**
 * Skeletal implementation of the {@link Automaton} interface.
 */
public abstract class AbstractAutomaton implements Automaton
{
    protected final String pattern;

    /**
     * @throws NullPointerException if {@code pattern} is null;
     */
    protected AbstractAutomaton(String pattern)
    {
        checkPointer(pattern != null);
        this.pattern = pattern;
    }

    @Override
    public String getPattern()
    {
        return pattern;
    }

    /**
     * @throws NullPointerException if {@code symbols} is null;
     */
    @Override
    public Automaton step(String symbols)
    {
        checkPointer(symbols != null);
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
