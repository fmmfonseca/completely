package com.miguelfonseca.completely.text.index;

import java.util.Arrays;

import javax.annotation.Nullable;

/**
 * Skeletal implementation of the {@link Index} interface.
 */
public abstract class AbstractIndex<V> implements Index<V>
{
    @Override
    public boolean put(String key, @Nullable V value)
    {
        return putAll(key, Arrays.asList(value));
    }

    @Override
    public boolean remove(String key, @Nullable V value)
    {
        return removeAll(key, Arrays.asList(value));
    }

    @Override
    public boolean remove(@Nullable V value)
    {
        return removeAll(Arrays.asList(value));
    }
}
