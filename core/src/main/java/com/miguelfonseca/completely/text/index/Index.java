package com.miguelfonseca.completely.text.index;

import java.util.Collection;
import java.util.Set;

import javax.annotation.Nullable;

/**
 * Associative data structure that maps text keys to multiple values.
 */
public interface Index<V>
{
    /**
     * Removes all key-value associations.
     */
    void clear();

    /**
     * Returns a {@link Set} of all values associated with a key.
     *
     * @throws NullPointerException if {@code key} is null;
     */
    Set<V> getAll(String key);

    /**
     * Returns {@code true} if no key-value associations exist.
     */
    boolean isEmpty();

    /**
     * Associates a single value with a key.
     *
     * @throws NullPointerException if {@code key} is null;
     */
    boolean put(String key, @Nullable V value);

    /**
     * Associates a collection of values with a key.
     *
     * @throws NullPointerException if {@code key} or {@code values} are null;
     */
    boolean putAll(String key, Collection<V> values);

    /**
     * Removes a single value associated with a key.
     *
     * @throws NullPointerException if {@code key} is null;
     */
    boolean remove(String key, @Nullable V value);

    /**
     * Removes a single value associated with any keys.
     */
    boolean remove(@Nullable V value);

    /**
     * Removes a collection of values associated with any keys.
     *
     * @throws NullPointerException if {@code values} is null;
     */
    boolean removeAll(Collection<V> values);

    /**
     * Removes all values associated with a key.
     *
     * @throws NullPointerException if {@code key} is null;
     */
    Set<V> removeAll(String key);

    /**
     * Removes a collection of values associated with a key.
     *
     * @throws NullPointerException if {@code key} or {@code values} are null;
     */
    boolean removeAll(String key, Collection<V> values);

    /**
     * Returns the number of key-value associations.
     */
    int size();
}
