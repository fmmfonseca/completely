package com.miguelfonseca.completely.data;

import java.util.Objects;

import javax.annotation.Nullable;

import static com.miguelfonseca.completely.common.Precondition.checkPointer;

/**
 * Decorator for scoring any object.
 */
public class ScoredObject<T> implements Comparable<ScoredObject>
{
    private final T object;
    private final Double score;

    /**
     * Constructs a new {@link ScoredObject}.
     */
    public ScoredObject(@Nullable T object, double score)
    {
        this.object = object;
        this.score = score;
    }

    /**
     * Returns the decorated object.
     */
    public T getObject()
    {
        return object;
    }

    /**
     * Returns the score.
     */
    public double getScore()
    {
        return score;
    }

    @Override
    public int compareTo(ScoredObject other)
    {
        checkPointer(other != null);
        return Double.compare(other.score, score);
    }

    private boolean equals(ScoredObject<?> other)
    {
        assert other != null;
        return Objects.equals(object, other.object) && Objects.equals(score, other.score);
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == this)
        {
            return true;
        }
        if (!(other instanceof ScoredObject))
        {
            return false;
        }
        return equals((ScoredObject<?>) other);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(object, score);
    }
}
