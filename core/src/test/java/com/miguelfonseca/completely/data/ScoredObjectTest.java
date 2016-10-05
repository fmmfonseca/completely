package com.miguelfonseca.completely.data;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@SuppressWarnings("checkstyle:multiplestringliterals")
public class ScoredObjectTest
{
    @Rule
    @SuppressWarnings("checkstyle:visibilitymodifier")
    public ExpectedException exceptionRule;

    public ScoredObjectTest()
    {
        this.exceptionRule = ExpectedException.none();
    }

    @Test
    public void testCompareTo()
    {
        ScoredObject<String> object1 = new ScoredObject<>("a", 0);
        ScoredObject<String> object2 = new ScoredObject<>("a", 1);
        assertEquals(1, object1.compareTo(object2));
        assertEquals(0, object1.compareTo(object1));
        assertEquals(-1, object2.compareTo(object1));
    }

    @Test
    public void testCreate()
    {
        ScoredObject<String> object = new ScoredObject<>("a", 1);
        assertEquals("a", object.getObject());
        assertEquals(1, object.getScore(), 0D);
    }

    @Test
    public void testEqualsConsistent()
    {
        ScoredObject<String> object1 = new ScoredObject<>("a", 0);
        ScoredObject<String> object2 = new ScoredObject<>("a", 0);
        assertEquals(object1, object2);
        assertEquals(object1, object2);
    }

    @Test
    public void testEqualsFail()
    {
        ScoredObject<String> object1 = new ScoredObject<>("a", 0);
        ScoredObject<String> object2 = new ScoredObject<>("a", 1);
        ScoredObject<String> object3 = new ScoredObject<>("b", 0);
        assertNotEquals(object1, object2);
        assertNotEquals(object1, object3);
    }

    @Test
    public void testEqualsNull()
    {
        ScoredObject<String> object = new ScoredObject<>("a", 0);
        assertNotEquals(object, null);
    }

    @Test
    public void testEqualsReflexive()
    {
        ScoredObject<String> object = new ScoredObject<>("a", 0);
        assertEquals(object, object);
    }

    @Test
    public void testEqualsSymmetric()
    {
        ScoredObject<String> object1 = new ScoredObject<>("a", 0);
        ScoredObject<String> object2 = new ScoredObject<>("a", 0);
        assertEquals(object1, object2);
        assertEquals(object2, object1);
    }

    @Test
    public void testEqualsTransitive()
    {
        ScoredObject<String> object1 = new ScoredObject<>("a", 0);
        ScoredObject<String> object2 = new ScoredObject<>("a", 0);
        ScoredObject<String> object3 = new ScoredObject<>("a", 0);
        assertEquals(object1, object2);
        assertEquals(object2, object3);
        assertEquals(object1, object3);
    }

    @Test
    public void testHashCodeConsistent()
    {
        ScoredObject<String> object = new ScoredObject<>("a", 0);
        assertEquals(object.hashCode(), object.hashCode());
        assertEquals(object.hashCode(), object.hashCode());
    }

    @Test
    public void testHashCodeCorrelation()
    {
        ScoredObject<String> object1 = new ScoredObject<>("a", 0);
        ScoredObject<String> object2 = new ScoredObject<>("a", 0);
        assertEquals(object1.hashCode(), object2.hashCode());
    }
}
