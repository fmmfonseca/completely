package completely;

import completely.data.Record;
import completely.text.index.HashMultiMap;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AutocompleteEngineTest
{
    private AutocompleteEngine<Record> engine;

    public AutocompleteEngineTest()
    {
        engine = new AutocompleteEngine.Builder<Record>()
            .setIndex(new HashMultiMap<Record>())
            .build();
    }

    @Test
    public void testSearchSort()
    {
        engine.add(new Record("a", 0));
        engine.add(new Record("a", 1));
        engine.add(new Record("a", 2));
        List<Record> result = engine.search("a");
        assertEquals(3, result.size());
        assertEquals(2, result.get(0).getScore(), 0);
        assertEquals(1, result.get(1).getScore(), 0);
        assertEquals(0, result.get(2).getScore(), 0);
    }

    @Test
    public void testSearchLimit()
    {
        engine.add(new Record("a", 0));
        engine.add(new Record("a", 1));
        engine.add(new Record("a", 2));
        List<Record> result = engine.search("a", 2);
        assertEquals(2, result.size());
        assertEquals(2, result.get(0).getScore(), 0);
        assertEquals(1, result.get(1).getScore(), 0);
    }
}
