package completely.text.index;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Hashing based implementation of the {@link Index} interface.
 *
 * <p>Note that this implementation is not synchronized.
 */
public class HashMultiMap<V> extends AbstractIndex<V> implements Index<V>
{
    private final Map<String, Set<V>> map;

    public HashMultiMap()
    {
        map = new HashMap<String, Set<V>>();
    }

    @Override
    public void clear()
    {
        map.clear();
    }

    @Override
    public Set<V> getAll(String key)
    {
        Set<V> value = map.get(key);
        return value != null ? Collections.unmodifiableSet(value) : Collections.<V>emptySet();
    }

    @Override
    public boolean isEmpty()
    {
        return map.isEmpty();
    }

    @Override
    public boolean put(String key, Collection<V> values)
    {
        if (key == null)
        {
            return false;
        }
        Set<V> value = map.get(key);
        if (value == null)
        {
            value = new HashSet<V>();
            map.put(key, value);
        }
        return value.addAll(values);
    }

    @Override
    public Set<V> remove(String key)
    {
        Set<V> value = map.remove(key);
        return value != null ? value : Collections.<V>emptySet();
    }

    @Override
    public boolean remove(String key, Collection<V> values)
    {
        Set<V> value = map.get(key);
        return value != null ? value.removeAll(values) : false;
    }

    @Override
    public int size()
    {
        int size = 0;
        for (Entry<String, Set<V>> entry : map.entrySet())
        {
            size += entry.getValue().size();
        }
        return size;
    }
}
