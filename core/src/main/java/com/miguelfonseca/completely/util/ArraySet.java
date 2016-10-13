package com.miguelfonseca.completely.util;

import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import javax.annotation.Nullable;

import static com.miguelfonseca.completely.common.Precondition.checkElement;
import static com.miguelfonseca.completely.common.Precondition.checkPointer;

/**
 * Array based implementation of the {@link Set} interface.
 *
 * <p>Note that this implementation is not synchronized.
 */
public class ArraySet<E> extends AbstractSet<E> implements Set<E>
{
    protected Object[] array;

    /**
     * Constructs a new {@link ArraySet}.
     */
    public ArraySet()
    {
        array = new Object[0];
    }

    @Override
    public boolean add(@Nullable E element)
    {
        int length = array.length;
        if (indexOf(element, array, 0, length) >= 0)
        {
            return false;
        }
        Object[] newArray = Arrays.copyOf(array, length + 1);
        newArray[length] = element;
        array = newArray;
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> elements)
    {
        checkPointer(elements != null);
        return addAll(elements.toArray());
    }

    @Override
    public Iterator<E> iterator()
    {
        return new Itr();
    }

    @Override
    public boolean remove(@Nullable Object element)
    {
        int length = array.length;
        int index = indexOf(element, array, 0, length);
        if (index < 0)
        {
            return false;
        }
        Object[] newArray = new Object[length - 1];
        System.arraycopy(array, 0, newArray, 0, index);
        System.arraycopy(array, index + 1, newArray, index, length - index - 1);
        array = newArray;
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> elements)
    {
        checkPointer(elements != null);
        int length = array.length;
        if (length > 0)
        {
            int newLength = 0;
            Object[] newArray = new Object[length];
            // Collect elements to preserve
            for (int i = 0; i < length; ++i)
            {
                Object element = array[i];
                if (!elements.contains(element))
                {
                    newArray[newLength++] = element;
                }
            }
            if (newLength != length)
            {
                // Trim
                array = Arrays.copyOf(newArray, newLength);
                return true;
            }
        }
        return false;
    }

    @Override
    public int size()
    {
        return array.length;
    }

    private boolean addAll(Object[] elements)
    {
        assert elements != null;
        if (elements.length <= 0)
        {
            return false;
        }
        int length = array.length;
        int added = 0;
        // Consolidate elements
        for (int i = 0; i < elements.length; ++i)
        {
            Object element = elements[i];
            if (indexOf(element, array, 0, length) < 0 && indexOf(element, elements, 0, added) < 0)
            {
                elements[added++] = element;
            }
        }
        if (added <= 0)
        {
            return false;
        }
        // Concatenate elements
        Object[] newArray = Arrays.copyOf(array, length + added);
        System.arraycopy(elements, 0, newArray, length, added);
        array = newArray;
        return true;
    }

    @SuppressWarnings("checkstyle:hiddenfield")
    private int indexOf(Object element, Object[] array, int fromIndex, int toIndex)
    {
        assert array != null;
        if (element == null)
        {
            for (int i = fromIndex; i < toIndex; ++i)
            {
                if (array[i] == null)
                {
                    return i;
                }
            }
        }
        else
        {
            for (int i = fromIndex; i < toIndex; ++i)
            {
                if (element.equals(array[i]))
                {
                    return i;
                }
            }
        }
        return -1;
    }

    private class Itr implements Iterator<E>
    {
        private int cursor;

        Itr()
        {
            cursor = 0;
        }

        public boolean hasNext()
        {
            return cursor < array.length;
        }

        @SuppressWarnings("unchecked")
        public E next()
        {
            checkElement(hasNext());
            return (E) array[cursor++];
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
}
