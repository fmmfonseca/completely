package com.miguelfonseca.completely.text.match;

import static com.miguelfonseca.completely.common.Precondition.checkPointer;

/**
 * Skeletal implementation of the {@link Automaton} interface.
 */
public abstract class AbstractAutomaton implements Automaton
{
    protected final String pattern, word;
    protected final int patternLength, wordLength;

    /**
     * @throws NullPointerException if {@code pattern} is null;
     */
    protected AbstractAutomaton(String pattern, String word)
    {
        checkPointer(pattern != null);
        checkPointer(word != null);
        this.pattern = pattern;
        this.patternLength = pattern.length();
        this.word = word;
        this.wordLength = word.length();
    }

    @Override
    public String getPattern()
    {
        return pattern;
    }

    @Override
    public String getWord()
    {
        return word;
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

    /**
     * @throws NullPointerException if {@code symbols} is null;
     */
    @Override
    public Automaton stepUntilWordAccepted(String symbols)
    {
        checkPointer(symbols != null);
        if (symbols.length() <= 0 || isWordAccepted())
        {
            return this;
        }
        else
        {
            return step(symbols.charAt(0)).stepUntilWordAccepted(symbols.substring(1));
        }
    }
}
