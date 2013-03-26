package completely.text.analyze.tokenize;

import completely.text.analyze.Analyzer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QGramTokenizerTest
{
    private Analyzer analyzer;

    @Test
    public void testApply()
    {
        analyzer = new QGramTokenizer(3);
        Object[] result = analyzer.apply("abcde").toArray();
        assertEquals(3, result.length);
        assertEquals("abc", result[0]);
        assertEquals("bcd", result[1]);
        assertEquals("cde", result[2]);
    }

    @Test
    public void testApplyVoid()
    {
        analyzer = new QGramTokenizer(3);
        Object[] result = analyzer.apply("ab").toArray();
        assertEquals(0, result.length);
    }
}
