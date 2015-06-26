package completely.text.match;

/**
 * Deterministic automaton simulator that matches equal words.
 */
public final class EqualityAutomaton extends AbstractAutomaton
{
    private final int length;
    private final int count;
    private final int index;

    /**
     * Constructs a new {@link EqualityAutomaton}.
     */
    public EqualityAutomaton(String pattern)
    {
        super(pattern);
        this.length = pattern.length();
        this.count = 0;
        this.index = 0;
    }

    private EqualityAutomaton(String pattern, int count, int index)
    {
        super(pattern);
        this.length = pattern.length();
        this.count = count;
        this.index = index;
    }

    @Override
    public boolean isWordAccepted()
    {
        return count == length && index == length;
    }

    @Override
    public boolean isWordRejected()
    {
        return count != index;
    }

    @Override
    public EqualityAutomaton step(char symbol)
    {
        int newCount = count + (index < length && pattern.charAt(index) == symbol ? 1 : 0);
        return new EqualityAutomaton(pattern, newCount, index + 1);
    }
}
