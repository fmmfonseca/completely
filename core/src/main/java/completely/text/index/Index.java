package completely.text.index;

import java.util.Collection;
import java.util.Set;

/**
 * Data structure that maps text keys to multiple values.
 */
public interface Index<V>
{
    /**
     * Removes all key-value associations.
     */
    void clear();

    /**
     * Returns a {@link Set} of all values associated with a key.
     */
    Set<V> getAll(String key);

    /**
     * Returns <tt>true</tt> if no key-value associations exist.
     */
    boolean isEmpty();

    /**
     * Associates a single value with a key.
     */
    boolean put(String key, V value);

    /**
     * Associates a collection of values with a key.
     */
    boolean putAll(String key, Collection<V> values);

    /**
     * Removes a single value associated with a key.
     */
    boolean remove(String key, V value);

    /**
     * Removes all values associated with a key.
     */
    Set<V> removeAll(String key);

    /**
     * Removes a collection of values associated with a key.
     */
    boolean removeAll(String key, Collection<V> values);

    /**
     * Returns the number of key-value associations.
     */
    int size();
}
