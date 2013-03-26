package completely.text.analyze.transform;

import completely.text.analyze.Analyzer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LowerCaseTransformerTest
{
    private Analyzer analyzer;

    public LowerCaseTransformerTest()
    {
        analyzer = new LowerCaseTransformer();
    }

    @Test
    public void testApply()
    {
        Object[] result = analyzer.apply("AbC").toArray();
        assertEquals("abc", result[0]);
    }
}
