package com.miguelfonseca.completely.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ArraySetTest
{
    @Rule
    @SuppressWarnings("checkstyle:visibilitymodifier")
    public ExpectedException exceptionRule;

    private ArraySet<Object> set;

    public ArraySetTest()
    {
        this.exceptionRule = ExpectedException.none();
        this.set = new ArraySet<>();
    }

    @Test
    public void testAdd()
    {
        assertTrue(set.add(0));
        assertEquals(1, set.size());
        assertTrue(set.add(1));
        assertEquals(2, set.size());
        assertFalse(set.add(1));
        assertEquals(2, set.size());
    }

    @Test
    public void testAddNull()
    {
        assertTrue(set.add(null));
        assertEquals(1, set.size());
        assertFalse(set.add(null));
        assertEquals(1, set.size());
    }

    @Test
    public void testAddAll()
    {
        assertTrue(set.addAll(Arrays.asList(0, 1)));
        assertEquals(2, set.size());
        assertTrue(set.addAll(Arrays.asList(2, 2)));
        assertEquals(3, set.size());
        assertFalse(set.addAll(Arrays.asList(1, 0)));
        assertEquals(3, set.size());
    }

    @Test
    public void testAddAllEmpty()
    {
        assertFalse(set.addAll(Arrays.asList()));
    }

    @Test
    public void testAddAllNull()
    {
        exceptionRule.expect(NullPointerException.class);
        set.addAll(null);
    }

    @Test
    public void testCreate()
    {
        assertEquals(0, set.size());
    }

    @Test
    public void testIterator()
    {
        set.addAll(Arrays.asList(0, 1, 2));
        Iterator<Object> it = set.iterator();
        assertTrue(it.hasNext());
        assertEquals(0, it.next());
        assertTrue(it.hasNext());
        assertEquals(1, it.next());
        assertTrue(it.hasNext());
        assertEquals(2, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    public void testEmptyIterator()
    {
        Iterator<Object> it = set.iterator();
        assertFalse(it.hasNext());
        exceptionRule.expect(NoSuchElementException.class);
        it.next();
    }

    @Test
    public void testRemove()
    {
        set.addAll(Arrays.asList(0, 1, 2));
        assertTrue(set.remove(1));
        assertEquals(2, set.size());
        assertTrue(set.remove(0));
        assertEquals(1, set.size());
        assertTrue(set.remove(2));
        assertEquals(0, set.size());
    }

    @Test
    public void testRemoveInexistent()
    {
        assertFalse(set.remove(1));
    }

    @Test
    public void testRemoveNull()
    {
        set.add(null);
        assertTrue(set.remove(null));
        assertEquals(0, set.size());
    }

    @Test
    public void testRemoveAll()
    {
        set.addAll(Arrays.asList(0, 1, 2));
        assertTrue(set.removeAll(Arrays.asList(0, 1)));
        assertEquals(1, set.size());
        assertTrue(set.removeAll(Arrays.asList(1, 2)));
        assertEquals(0, set.size());
    }

    @Test
    public void testRemoveAllEmpty()
    {
        assertFalse(set.removeAll(Arrays.asList()));
    }

    @Test
    public void testRemoveAllNull()
    {
        exceptionRule.expect(NullPointerException.class);
        set.removeAll(null);
    }
}
