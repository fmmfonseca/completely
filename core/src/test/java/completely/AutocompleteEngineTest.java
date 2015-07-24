package completely;

import completely.data.TestRecord;
import completely.text.index.HashMultiMap;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("checkstyle:multiplestringliterals")
public class AutocompleteEngineTest
{
    @Rule
    @SuppressWarnings("checkstyle:visibilitymodifier")
    public ExpectedException exceptionRule;

    private AutocompleteEngine<TestRecord> engine;

    public AutocompleteEngineTest()
    {
        this.exceptionRule = ExpectedException.none();
        this.engine = new AutocompleteEngine.Builder<TestRecord>()
            .setIndex(new HashMultiMap<TestRecord>())
            .build();
    }

    @Test
    public void testCreateEmpty()
    {
        exceptionRule.expect(NullPointerException.class);
        new AutocompleteEngine.Builder<TestRecord>().build();
    }

    @Test
    public void testAdd()
    {
        TestRecord record = new TestRecord(0, "a");
        assertTrue(engine.add(record));
        assertFalse(engine.add(record));
    }

    @Test
    public void testAddNull()
    {
        exceptionRule.expect(NullPointerException.class);
        engine.add(null);
    }

    @Test
    public void testAddAllNull()
    {
        exceptionRule.expect(NullPointerException.class);
        engine.addAll(null);
    }

    @Test
    public void testSearch()
    {
        engine.add(new TestRecord(0, "a", "b"));
        engine.add(new TestRecord(0, "a", "c"));
        engine.add(new TestRecord(0, "a", "d"));
        assertEquals(3, engine.search("a").size());
        assertEquals(1, engine.search("b").size());
        assertEquals(1, engine.search("c").size());
        assertEquals(1, engine.search("d").size());
    }

    @Test
    public void testSearchLimit()
    {
        engine.add(new TestRecord(0, "a"));
        engine.add(new TestRecord(0, "a"));
        engine.add(new TestRecord(0, "a"));
        assertEquals(2, engine.search("a", 2).size());
    }

    @Test
    public void testSearchNullQuery()
    {
        exceptionRule.expect(NullPointerException.class);
        engine.search(null);
    }
}
