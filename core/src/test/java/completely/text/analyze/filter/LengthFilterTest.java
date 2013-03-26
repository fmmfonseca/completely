package completely.text.analyze.filter;

import completely.text.analyze.Analyzer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LengthFilterTest
{
    private Analyzer analyzer;

    @Test
    public void testApply()
    {
        analyzer = new LengthFilter(2, 3);
        Object[] result = analyzer.apply("a", "aa", "aaa", "aaaa").toArray();
        assertEquals(2, result.length);
        assertEquals("aa", result[0]);
        assertEquals("aaa", result[1]);
    }
}
