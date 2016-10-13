package com.miguelfonseca.completely;

import com.miguelfonseca.completely.data.ScoredObject;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("checkstyle:multiplestringliterals")
public class AggregatorTest
{
    @Rule
    @SuppressWarnings("checkstyle:visibilitymodifier")
    public ExpectedException exceptionRule;

    private Aggregator<String> aggregator;

    public AggregatorTest()
    {
        this.aggregator = new Aggregator<>();
        this.exceptionRule = ExpectedException.none();
    }

    @Test
    public void testCreate()
    {
        assertTrue(aggregator.isEmpty());
        assertEquals(0, aggregator.size());
        assertEquals(0, aggregator.values().size());
    }

    @Test
    public void testAdd()
    {
        assertTrue(aggregator.add(new ScoredObject<>("a", 0)));
        assertEquals(1, aggregator.size());
        assertFalse(aggregator.add(new ScoredObject<>("a", 0)));
        assertEquals(1, aggregator.size());
        assertTrue(aggregator.add(new ScoredObject<>("b", 0)));
        assertEquals(2, aggregator.size());
        assertTrue(aggregator.add(new ScoredObject<>("a", 1)));
        assertEquals(2, aggregator.size());
    }

    @Test
    public void testAddNull()
    {
        exceptionRule.expect(NullPointerException.class);
        aggregator.add(null);
    }

    @Test
    public void testAddAllNull()
    {
        exceptionRule.expect(NullPointerException.class);
        aggregator.addAll(null);
    }

    @Test
    public void testRetain()
    {
        aggregator.add(new ScoredObject<>("a", 0));
        aggregator.add(new ScoredObject<>("b", 0));
        assertTrue(aggregator.retain(new ScoredObject<>("a", 0)));
        assertEquals(1, aggregator.size());
        assertTrue(aggregator.retain(new ScoredObject<>("a", 1)));
        assertEquals(1, aggregator.size());
        assertTrue(aggregator.retain(new ScoredObject<>("a", 1)));
        assertEquals(1, aggregator.size());
        assertTrue(aggregator.retain(new ScoredObject<>("b", 0)));
        assertEquals(0, aggregator.size());
    }

    @Test
    public void testRetainNull()
    {
        exceptionRule.expect(NullPointerException.class);
        aggregator.retain(null);
    }

    @Test
    public void testRetainAllNull()
    {
        exceptionRule.expect(NullPointerException.class);
        aggregator.retainAll(null);
    }

    @Test
    public void testValues()
    {
        aggregator.add(new ScoredObject<>("a", 0));
        aggregator.add(new ScoredObject<>("b", 0));
        List<String> result = aggregator.values();
        assertEquals(2, result.size());
    }

    @Test
    public void testValuesOrder()
    {
        aggregator.add(new ScoredObject<>("c", 0));
        aggregator.add(new ScoredObject<>("b", 0));
        aggregator.add(new ScoredObject<>("b", 1));
        aggregator.add(new ScoredObject<>("a", 2));
        List<String> result = aggregator.values();
        assertEquals("a", result.get(0));
        assertEquals("b", result.get(1));
        assertEquals("c", result.get(2));
    }
}
