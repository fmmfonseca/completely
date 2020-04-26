package com.miguelfonseca.completely;

import com.miguelfonseca.completely.data.TestRecord;
import com.miguelfonseca.completely.text.index.HashMultiMap;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("checkstyle:multiplestringliterals")
public class AutocompleteEngineTest
{
    private AutocompleteEngine<TestRecord> engine;

    public AutocompleteEngineTest()
    {
        this.engine = new AutocompleteEngine.Builder<TestRecord>()
            .setIndex(new HashMultiMap<>())
            .build();
    }

    @Test
    public void testCreateEmpty()
    {
        assertThrows(
            NullPointerException.class,
            () -> new AutocompleteEngine.Builder<TestRecord>().build()
        );
    }

    @Test
    public void testAdd()
    {
        TestRecord record = new TestRecord("a");
        assertTrue(engine.add(record));
        assertFalse(engine.add(record));
    }

    @Test
    public void testAddNull()
    {
        assertThrows(
            NullPointerException.class,
            () -> engine.add(null)
        );
    }

    @Test
    public void testAddAllNull()
    {
        assertThrows(
            NullPointerException.class,
            () -> engine.addAll(null)
        );
    }

    @Test
    public void testRemove()
    {
        TestRecord record = new TestRecord("a");
        engine.add(record);
        assertTrue(engine.remove(record));
        assertFalse(engine.remove(record));
    }

    @Test
    public void testRemoveNull()
    {
        assertThrows(
            NullPointerException.class,
            () -> engine.remove(null)
        );
    }

    @Test
    public void testRemoveAllNull()
    {
        assertThrows(
            NullPointerException.class,
            () -> engine.removeAll(null)
        );
    }

    @Test
    public void testSearch()
    {
        engine.add(new TestRecord("a", "b"));
        engine.add(new TestRecord("a", "c"));
        engine.add(new TestRecord("a", "d"));
        assertEquals(3, engine.search("a").size());
        assertEquals(1, engine.search("b").size());
        assertEquals(1, engine.search("c").size());
        assertEquals(1, engine.search("d").size());
    }

    @Test
    public void testSearchLimit()
    {
        engine.add(new TestRecord("a"));
        engine.add(new TestRecord("a"));
        engine.add(new TestRecord("a"));
        assertEquals(2, engine.search("a", 2).size());
    }

    @Test
    public void testSearchNegativeLimit()
    {
        assertThrows(
            IllegalArgumentException.class,
            () -> engine.search("", -1)
        );
    }

    @Test
    public void testSearchNullQuery()
    {
        assertThrows(
            NullPointerException.class,
            () -> engine.search(null)
        );
    }
}
