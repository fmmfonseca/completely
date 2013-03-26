package completely.text.analyze.transform;

import completely.text.analyze.Analyzer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DiacriticsTransformerTest
{
    private Analyzer analyzer;

    public DiacriticsTransformerTest()
    {
        analyzer = new DiacriticsTransformer();
    }

    @Test
    public void testApply()
    {
        Object[] result = analyzer.apply("àbç").toArray();
        assertEquals("abc", result[0]);
    }
}
