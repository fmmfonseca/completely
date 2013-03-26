package completely;

import java.util.Collection;

/**
 * Adapter for any index data structure.
 */
public interface IndexAdapter<V>
{
    Collection<V> get(String token);

    boolean put(String token, V value);
}
