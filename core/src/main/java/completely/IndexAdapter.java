package completely;

import java.util.Collection;

import javax.annotation.Nullable;

/**
 * Adapter for any index data structure.
 */
public interface IndexAdapter<V>
{
    /**
     * Returns a {@link Collection} of all values associated with a token.
     */
    Collection<V> get(String token);

    /**
     * Associates a single value with a token.
     */
    boolean put(String token, @Nullable V value);
}
