package completely;

import java.util.Collection;

import javax.annotation.Nullable;

/**
 * Adapter for any index data structure.
 */
public interface IndexAdapter<V>
{
    Collection<V> get(String token);

    boolean put(String token, @Nullable V value);
}
