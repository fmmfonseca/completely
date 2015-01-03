package completely.text.analyze.filter;

import completely.text.analyze.AnalyzerTest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NullFilterTest extends AnalyzerTest<NullFilter>
{
    public NullFilterTest()
    {
        super(new NullFilter());
    }

    @Test
    public void testApply()
    {
        Object[] result = analyzer.apply("a", null, "ab", null).toArray();
        assertEquals(2, result.length);
        assertEquals("a", result[0]);
        assertEquals("ab", result[1]);
    }
}
