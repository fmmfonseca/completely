package com.miguelfonseca.completely.text.match;

/**
 * Deterministic automaton simulator that matches equal words.
 */
public final class EqualityAutomaton extends AbstractAutomaton
{
    private final int count;

    /**
     * Constructs a new {@link EqualityAutomaton}.
     *
     * @throws NullPointerException if {@code pattern} is null;
     */
    public EqualityAutomaton(String pattern)
    {
        super(pattern, "");
        this.count = 0;
    }

    private EqualityAutomaton(String pattern, String word, int count)
    {
        super(pattern, word);
        this.count = count;
    }

    @Override
    public double getScore()
    {
        int length = Math.max(patternLength, wordLength);
        if (length == 0)
        {
            return 1;
        }
        return count / (double) length;
    }

    @Override
    public boolean isWordAccepted()
    {
        return count == patternLength && wordLength == patternLength;
    }

    @Override
    public boolean isWordRejected()
    {
        return count != wordLength;
    }

    @Override
    public EqualityAutomaton step(char symbol)
    {
        boolean match = wordLength < patternLength && pattern.charAt(wordLength) == symbol;
        int newCount = count + (match ? 1 : 0);
        return new EqualityAutomaton(pattern, word + symbol, newCount);
    }
}
