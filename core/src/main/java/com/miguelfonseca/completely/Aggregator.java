package com.miguelfonseca.completely;

import com.miguelfonseca.completely.data.ScoredObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import static com.miguelfonseca.completely.common.Precondition.checkPointer;

/**
 * Aggregator to collect, merge and transform {@link ScoredObject} elements.
 */
public class Aggregator<T>
{
    private final Map<T, Double> scores;
    private final Comparator<ScoredObject<T>> comparator;

    /**
     * Constructs a new {@link Aggregator}.
     */
    public Aggregator()
    {
        this(null);
    }

    /**
     * Constructs a new {@link Aggregator}.
     */
    public Aggregator(@Nullable Comparator<ScoredObject<T>> comparator)
    {
        this.scores = new HashMap<>();
        this.comparator = comparator;
    }

    /**
     * Adds a single element, if not present already.
     *
     * @throws NullPointerException if {@code element} is null;
     */
    public boolean add(ScoredObject<T> element)
    {
        return addAll(Arrays.asList(element));
    }

    /**
     * Adds a collection of elements, if not present already.
     *
     * @throws NullPointerException if {@code elements} is null or contains a null element;
     */
    public boolean addAll(Collection<ScoredObject<T>> elements)
    {
        checkPointer(elements != null);
        boolean result = false;
        for (ScoredObject<T> element : elements)
        {
            checkPointer(element != null);
            Double score = scores.get(element.getObject());
            if (score == null)
            {
                scores.put(element.getObject(), element.getScore());
                result = true;
            }
            else if (element.getScore() != 0)
            {
                scores.put(element.getObject(), score + element.getScore());
                result = true;
            }
        }
        return result;
    }

    /**
     * Returns {@code true} if no elements exist.
     */
    public boolean isEmpty()
    {
        return scores.isEmpty();
    }

    /**
     * Retain the elements in common, compared according to the objects scored.
     *
     * @throws NullPointerException if {@code element} is null;
     */
    public boolean retain(ScoredObject<T> element)
    {
        return retainAll(Arrays.asList(element));
    }

    /**
     * Retains the elements in common, compared according to the objects scored.
     *
     * @throws NullPointerException if {@code elements} is null or contains a null element;
     */
    public boolean retainAll(Collection<ScoredObject<T>> elements)
    {
        checkPointer(elements != null);
        // Intersect
        Collection<T> set = new HashSet<>();
        for (ScoredObject<T> element : elements)
        {
            checkPointer(element != null);
            set.add(element.getObject());
        }
        boolean result = scores.keySet().retainAll(set);
        // Combine scores
        for (ScoredObject<T> element : elements)
        {
            if (element.getScore() == 0)
            {
                continue;
            }
            Double score = scores.get(element.getObject());
            if (score != null)
            {
                scores.put(element.getObject(), score + element.getScore());
                result = true;
            }
        }
        return result;
    }

    /**
     * Returns the number of elements.
     */
    public int size()
    {
        return scores.size();
    }

    /**
     * Returns a {@link List} of all objects scored, sorted according to the default comparator.
     */
    public List<T> values()
    {
        List<ScoredObject<T>> list = new ArrayList<>();
        for (Entry<T, Double> entry : scores.entrySet())
        {
            list.add(new ScoredObject<>(entry.getKey(), entry.getValue()));
        }
        Collections.sort(list, comparator);
        List<T> result = new ArrayList<>();
        for (ScoredObject<T> element : list)
        {
            result.add(element.getObject());
        }
        return result;
    }
}
