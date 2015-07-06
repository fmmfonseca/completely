package completely;

import completely.text.index.HashMultiMap;

import java.util.Comparator;
import java.util.List;

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
            .setIndex(new HashMultiMap<>())
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

    /**
     * Because all 3 records are equal (but different instances) only 1 may be returned as search hit.
     */
    @Test
    public void testEqualRecords()
    {
        AutocompleteEngine<TestRecordImplementingEqualsHashcode> engine = new AutocompleteEngine.Builder<TestRecordImplementingEqualsHashcode>()
                .setIndex(new HashMultiMap<>())
                .build();
        engine.add(new TestRecordImplementingEqualsHashcode(0, "a"));
        engine.add(new TestRecordImplementingEqualsHashcode(0, "a"));
        engine.add(new TestRecordImplementingEqualsHashcode(0, "a"));
        assertEquals(1, engine.search("a", 2).size());
    }

    @Test
    public void testSearchCustomComparator()
    {
        Comparator<TestRecord> comparator = new Comparator<TestRecord>()
        {
            @Override
            public int compare(TestRecord o1, TestRecord o2)
            {
                Double score1 = o1.getScore();
                Double score2 = o2.getScore();
                return score1.compareTo(score2);
            }
        };
        engine.add(new TestRecord(0, "a"));
        engine.add(new TestRecord(1, "a"));
        engine.add(new TestRecord(2, "a"));
        List<TestRecord> result = engine.search("a", comparator);
        assertEquals(3, result.size());
        assertEquals(0, result.get(0).getScore(), 0);
        assertEquals(1, result.get(1).getScore(), 0);
        assertEquals(2, result.get(2).getScore(), 0);
    }

    @Test
    public void testSearchNullComparator()
    {
        engine.add(new TestRecord(0, "a"));
        engine.add(new TestRecord(1, "a"));
        engine.add(new TestRecord(2, "a"));
        List<TestRecord> result = engine.search("a", null);
        assertEquals(3, result.size());
    }

    /**
     * Not allowing search(null) see see https://github.com/fmmfonseca/completely/issues/2
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSearchForNull()
    {
        engine.add(new TestRecord(0, "alpha"));
        engine.add(new TestRecord(0, "beta"));
        engine.search(null);
    }
    /**
     * Not allowing search("") see see https://github.com/fmmfonseca/completely/issues/2
     */
    @Test(expected = IllegalArgumentException.class)
    public void testSearchForEmpty()
    {
        engine.add(new TestRecord(0, "alpha"));
        engine.add(new TestRecord(0, "beta"));
        engine.search("");
    }


}
