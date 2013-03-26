package completely.text.index;

import java.util.Set;

/**
 * {@link Index} with approximate key matching.
 */
public interface FuzzyIndex<V> extends Index<V>
{
    /**
     * Returns a {@link Set} of all values associated with a key fragment.
     */
    Set<V> getAny(String fragment);
}
