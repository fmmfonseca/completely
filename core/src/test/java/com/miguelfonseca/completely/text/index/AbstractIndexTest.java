package com.miguelfonseca.completely.text.index;

import java.util.Collection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("checkstyle:multiplestringliterals")
public abstract class AbstractIndexTest<T extends Index<Object>>
{
    protected T index;

    public AbstractIndexTest(T index)
    {
        this.index = index;
    }

    @Test
    public void testClear()
    {
        index.put("abc", 0);
        index.clear();
        assertTrue(index.isEmpty());
        assertEquals(0, index.size());
    }

    @Test
    public void testCreate()
    {
        assertTrue(index.isEmpty());
        assertEquals(0, index.size());
    }

    @Test
    public void testGetAll()
    {
        index.put("abc", 0);
        index.put("abc", 1);
        Collection<Object> result = index.getAll("abc");
        assertEquals(2, result.size());
        assertTrue(result.contains(0));
        assertTrue(result.contains(1));
    }

    @Test
    public void testGetAllInexistent()
    {
        assertTrue(index.getAll("abc").isEmpty());
        index.put("abcd", 0);
        assertTrue(index.getAll("abc").isEmpty());
    }

    @Test
    public void testGetAllNull()
    {
        assertThrows(
            NullPointerException.class,
            () -> index.getAll(null)
        );
    }

    @Test
    public void testPut()
    {
        assertTrue(index.put("abcd", 0));
        assertEquals(1, index.size());
        assertTrue(index.put("abc", 0));
        assertEquals(2, index.size());
    }

    @Test
    public void testPutDuplicateKey()
    {
        assertTrue(index.put("abc", 0));
        assertTrue(index.put("abc", 1));
        assertEquals(2, index.size());
    }

    @Test
    public void testPutDuplicateValue()
    {
        assertTrue(index.put("abc", 0));
        assertFalse(index.put("abc", 0));
        assertEquals(1, index.size());
    }

    @Test
    public void testPutNullKey()
    {
        assertThrows(
            NullPointerException.class,
            () -> index.put(null, 0)
        );
    }

    @Test
    public void testPutNullValue()
    {
        assertTrue(index.put("abc", null));
        assertEquals(1, index.size());
    }

    @Test
    public void testPutAllNullValues()
    {
        assertThrows(
            NullPointerException.class,
            () -> index.putAll("abc", null)
        );
    }

    @Test
    public void testRemoveKeyValue()
    {
        index.put("abc", 0);
        index.put("abcd", 1);
        index.put("abcd", 0);
        assertTrue(index.remove("abc", 0));
        assertEquals(2, index.size());
        assertTrue(index.remove("abcd", 1));
        assertEquals(1, index.size());
        assertTrue(index.remove("abcd", 0));
        assertEquals(0, index.size());
    }

    @Test
    public void testRemoveInexistentKeyValue()
    {
        assertFalse(index.remove("abc", 0));
        index.put("abcd", 0);
        assertFalse(index.remove("abc", 0));
        assertFalse(index.remove("abcd", 1));
    }

    @Test
    public void testRemoveNullKeyValue()
    {
        assertThrows(
            NullPointerException.class,
            () -> index.remove(null, 0)
        );
    }

    @Test
    public void testRemoveValue()
    {
        index.put("abc", 0);
        index.put("abcd", 1);
        index.put("abcd", 0);
        assertTrue(index.remove(0));
        assertEquals(1, index.size());
        assertTrue(index.remove(1));
        assertEquals(0, index.size());
    }

    @Test
    public void testRemoveInexistentValue()
    {
        assertFalse(index.remove(0));
        index.put("abc", 1);
        assertFalse(index.remove(0));
    }

    @Test
    public void testRemoveNullValue()
    {
        index.put("abc", null);
        index.put("abcd", null);
        assertTrue(index.remove("abc", null));
        assertEquals(1, index.size());
        assertTrue(index.remove(null));
        assertEquals(0, index.size());
    }

    @Test
    public void testRemoveAllKey()
    {
        index.put("abc", 0);
        index.put("abcd", 0);
        assertEquals(1, index.removeAll("abc").size());
        assertEquals(1, index.removeAll("abcd").size());
    }

    @Test
    public void testRemoveAllInexistentKey()
    {
        assertTrue(index.removeAll("abc").isEmpty());
        index.put("abcd", 0);
        assertTrue(index.removeAll("abc").isEmpty());
    }

    @Test
    public void testRemoveAllNullKey()
    {
        assertThrows(
            NullPointerException.class,
            () -> index.removeAll((String) null)
        );
    }

    @Test
    public void testRemoveAllNullValues()
    {
        assertThrows(
            NullPointerException.class,
            () -> index.removeAll((Collection<Object>) null)
        );
    }
}
