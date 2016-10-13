package com.miguelfonseca.completely;

import com.miguelfonseca.completely.data.ScoredObject;

import java.util.Collection;

import javax.annotation.Nullable;

/**
 * Adapter for any index data structure.
 */
public interface IndexAdapter<T>
{
    /**
     * Returns a {@link Collection} of all values associated with a token.
     */
    Collection<ScoredObject<T>> get(String token);

    /**
     * Associates a single value with a token.
     */
    boolean put(String token, @Nullable T value);

    /**
     * Removes a single value associated with any tokens.
     */
    boolean remove(T value);
}
