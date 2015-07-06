package completely;

import completely.text.analyze.tokenize.WordTokenizer;
import completely.text.index.HashMultiMap;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class AutocompleteEngine_WithTokenizer_Test {


    @Test
    public void testWithWordTokenizer()
    {
        AutocompleteEngine<TestRecord> engine = new AutocompleteEngine.Builder<TestRecord>()
                .setAnalyzer(new WordTokenizer())
                .setIndex(new HashMultiMap<>())
                .build();

        engine.add(new TestRecord(0, "spaghetti pizza"));
        engine.add(new TestRecord(0, "spaghetti cannelloni"));
        engine.add(new TestRecord(0, "caprese spaghetti"));

        List<TestRecord> result = engine.search("spaghetti");
        assertEquals(3, result.size());

        result = engine.search("pizza spaghetti");
        assertEquals(1, result.size());

        result = engine.search("pizza mamaliga spaghetti"); //no record contains mamaliga
        assertEquals(0, result.size());
    }


}
