package completely;

import completely.data.Record;
import completely.data.SimpleRecord;
import completely.text.analyze.Analyzer;
import completely.text.index.HashMultiMap;
import completely.text.index.Index;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AutocompleteEngineTest
{
    private AutocompleteEngine<Record> engine;

    public AutocompleteEngineTest()
    {
        Index<Record> index = new HashMultiMap<Record>();
        Analyzer analyzer = new Analyzer()
        {
            @Override
            public Collection<String> apply(Collection<String> input)
            {
                return new ArrayList<String>(input);
            }
        };
        engine = new AutocompleteEngine<Record>(index, analyzer);
    }

    @Test
    public void testSearchSort()
    {
        engine.add(new SimpleRecord("a", 0));
        engine.add(new SimpleRecord("a", 1));
        engine.add(new SimpleRecord("a", 2));
        List<Record> result = engine.search("a");
        assertEquals(3, result.size());
        assertEquals(2, result.get(0).getScore(), 0);
        assertEquals(1, result.get(1).getScore(), 0);
        assertEquals(0, result.get(2).getScore(), 0);
    }

    @Test
    public void testSearchLimit()
    {
        engine.add(new SimpleRecord("a", 0));
        engine.add(new SimpleRecord("a", 1));
        engine.add(new SimpleRecord("a", 2));
        List<Record> result = engine.search("a", 2);
        assertEquals(2, result.size());
        assertEquals(2, result.get(0).getScore(), 0);
        assertEquals(1, result.get(1).getScore(), 0);
    }
}
