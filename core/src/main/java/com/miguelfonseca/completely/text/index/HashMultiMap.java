package com.miguelfonseca.completely.text.index;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static com.miguelfonseca.completely.common.Precondition.checkPointer;

/**
 * Hashing based implementation of the {@link Index} interface.
 *
 * <p>Note that this implementation is not synchronized.
 */
public class HashMultiMap<V> extends AbstractIndex<V> implements Index<V>
{
    private final Map<String, Set<V>> map;

    /**
     * Constructs a new {@link HashMultiMap}.
     */
    public HashMultiMap()
    {
        map = new HashMap<>();
    }

    @Override
    public void clear()
    {
        map.clear();
    }

    @Override
    public Set<V> getAll(String key)
    {
        checkPointer(key != null);
        Set<V> value = map.get(key);
        if (value != null)
        {
            return new HashSet<>(value);
        }
        return new HashSet<>();
    }

    @Override
    public boolean isEmpty()
    {
        return map.isEmpty();
    }

    @Override
    public boolean putAll(String key, Collection<V> values)
    {
        checkPointer(key != null);
        checkPointer(values != null);
        Set<V> value = map.get(key);
        if (value == null)
        {
            value = new HashSet<>();
            map.put(key, value);
        }
        return value.addAll(values);
    }

    @Override
    public boolean removeAll(Collection<V> values)
    {
        checkPointer(values != null);
        boolean result = false;
        for (Iterator<Set<V>> iterator = map.values().iterator(); iterator.hasNext();)
        {
            Set<V> value = iterator.next();
            if (value.removeAll(values))
            {
                result = true;
            }
            if (value.isEmpty())
            {
                iterator.remove();
            }
        }
        return result;
    }

    @Override
    public Set<V> removeAll(String key)
    {
        checkPointer(key != null);
        Set<V> value = map.remove(key);
        return value != null ? value : new HashSet<>();
    }

    @Override
    public boolean removeAll(String key, Collection<V> values)
    {
        checkPointer(key != null);
        checkPointer(values != null);
        Set<V> value = map.get(key);
        if (value != null)
        {
            boolean result = value.removeAll(values);
            if (value.isEmpty())
            {
                map.remove(key);
            }
            return result;
        }
        return false;
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
