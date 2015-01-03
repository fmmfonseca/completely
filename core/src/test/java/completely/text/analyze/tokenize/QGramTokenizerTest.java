package completely.text.analyze.tokenize;

import completely.text.analyze.AnalyzerTest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QGramTokenizerTest extends AnalyzerTest<QGramTokenizer>
{
    public QGramTokenizerTest()
    {
        super(new QGramTokenizer(3));
    }

    @Test
    public void testApply()
    {
        Object[] result = analyzer.apply("abcde").toArray();
        assertEquals(3, result.length);
        assertEquals("abc", result[0]);
        assertEquals("bcd", result[1]);
        assertEquals("cde", result[2]);
    }

    @Test
    public void testApplyVoid()
    {
        Object[] result = analyzer.apply("ab").toArray();
        assertEquals(0, result.length);
    }


    @Test
    public void testApplyNullElement()
    {
        exceptionRule.expect(NullPointerException.class);
        analyzer.apply((String) null);
    }
}
