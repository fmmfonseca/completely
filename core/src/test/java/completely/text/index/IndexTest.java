package completely.text.index;

import java.util.Collection;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public abstract class IndexTest<T extends Index<Object>>
{
    protected T index;

    public IndexTest(T index)
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
    public void testGet()
    {
        index.put("abc", 0);
        index.put("abc", 1);
        Collection<Object> result = index.getAll("abc");
        assertEquals(2, result.size());
        assertTrue(result.contains(0));
        assertTrue(result.contains(1));
    }

    @Test
    public void testGetInexistent()
    {
        assertTrue(index.getAll("abc").isEmpty());
    }

    @Test
    public void testGetNullKey()
    {
        assertTrue(index.getAll(null).isEmpty());
    }

    @Test
    public void testPut()
    {
        assertTrue(index.put("abc", 0));
        assertEquals(1, index.size());
        assertTrue(index.put("abcd", 0));
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
        assertFalse(index.put(null, 0));
    }

    @Test
    public void testPutNullValue()
    {
        assertTrue(index.put("abc", null));
        assertEquals(1, index.size());
    }

    @Test
    public void testRemoveKey()
    {
        index.put("abc", 0);
        index.put("abcd", 0);
        assertEquals(1, index.removeAll("abc").size());
    }

    @Test
    public void testRemoveInexistentKey()
    {
        assertTrue(index.removeAll("abc").isEmpty());
    }

    @Test
    public void testRemoveNullKey()
    {
        assertTrue(index.removeAll((String) null).isEmpty());
    }

    @Test
    public void testRemoveKeyValue()
    {
        index.put("abc", 0);
        index.put("abcd", 0);
        assertTrue(index.remove("abc", 0));
        assertEquals(1, index.size());
    }

    @Test
    public void testRemoveInexistentKeyValue()
    {
        assertFalse(index.remove("abc", 0));
    }

    @Test
    public void testRemoveValue()
    {
        index.put("abc", 0);
        index.put("abcd", 0);
        assertTrue(index.remove(0));
        assertEquals(0, index.size());
    }

    @Test
    public void testRemoveInexistentValue()
    {
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
}
