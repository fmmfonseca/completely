package completely;

import completely.data.Indexable;
import completely.text.index.HashMultiMap;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AutocompleteEngineTest
{
    private AutocompleteEngine<TestRecord> engine;

    public AutocompleteEngineTest()
    {
        engine = new AutocompleteEngine.Builder<TestRecord>()
            .setIndex(new HashMultiMap<TestRecord>())
            .build();
    }

    @Test
    public void testSearchSort()
    {
        engine.add(new TestRecord("a", 0));
        engine.add(new TestRecord("a", 1));
        engine.add(new TestRecord("a", 2));
        List<TestRecord> result = engine.search("a");
        assertEquals(3, result.size());
        assertEquals(2, result.get(0).getScore(), 0);
        assertEquals(1, result.get(1).getScore(), 0);
        assertEquals(0, result.get(2).getScore(), 0);
    }

    @Test
    public void testSearchLimit()
    {
        engine.add(new TestRecord("a", 0));
        engine.add(new TestRecord("a", 1));
        engine.add(new TestRecord("a", 2));
        List<TestRecord> result = engine.search("a", 2);
        assertEquals(2, result.size());
        assertEquals(2, result.get(0).getScore(), 0);
        assertEquals(1, result.get(1).getScore(), 0);
    }

    private static class TestRecord implements Indexable
    {
        private final String text;
        private final double score;

        TestRecord(String text, double score)
        {
            this.text = text;
            this.score = score;
        }

        @Override
        public String getText()
        {
            return text;
        }

        @Override
        public double getScore()
        {
            return score;
        }
    }
}
