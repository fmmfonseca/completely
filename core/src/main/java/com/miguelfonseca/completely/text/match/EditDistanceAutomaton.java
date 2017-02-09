package com.miguelfonseca.completely.text.match;

/**
 * Nondeterministic automaton simulator that matches words within edit distance.
 */
public final class EditDistanceAutomaton extends AbstractAutomaton
{
    private final int size;
    private final double threshold;
    private final int[] vector;

    /**
     * Constructs a new {@link EditDistanceAutomaton}.
     *
     * @throws NullPointerException if {@code pattern} is null;
     */
    public EditDistanceAutomaton(String pattern, double threshold)
    {
        super(pattern, "");
        this.size = patternLength + 1;
        this.threshold = threshold;
        this.vector = new int[size];
        for (int i = 0; i < size; ++i)
        {
            this.vector[i] = i;
        }
    }

    private EditDistanceAutomaton(String pattern, String word, double threshold, int[] vector)
    {
        super(pattern, word);
        this.size = patternLength + 1;
        this.threshold = threshold;
        this.vector = vector;
    }

    private int getDistance()
    {
        return vector[size - 1];
    }

    @Override
    public double getScore()
    {
        int length = Math.max(patternLength, wordLength);
        if (length == 0)
        {
            return 1;
        }
        return 1 - getDistance() / (double) length;
    }

    @Override
    public boolean isWordAccepted()
    {
        return getDistance() <= threshold;
    }

    @Override
    public boolean isWordRejected()
    {
        for (int i : vector)
        {
            if (i <= threshold)
            {
                return false;
            }
        }
        return true;
    }

    private int min(int a, int b, int c)
    {
        return a <= b && a <= c ? a : b <= c ? b : c;
    }

    @Override
    public EditDistanceAutomaton step(char symbol)
    {
        int[] newVector = new int[size];
        newVector[0] = vector[0] + 1;
        for (int i = 1; i < size; ++i)
        {
            if (pattern.charAt(i - 1) == symbol)
            {
                newVector[i] = vector[i - 1];
            }
            else
            {
                newVector[i] = min(newVector[i - 1], vector[i], vector[i - 1]) + 1;
            }
        }
        return new EditDistanceAutomaton(pattern, word + symbol, threshold, newVector);
    }
}
