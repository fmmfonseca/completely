package com.miguelfonseca.completely.text.analyze;

import com.miguelfonseca.completely.text.analyze.tokenize.QGramTokenizer;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("checkstyle:multiplestringliterals")
public class ChainedAnalyzerTest extends AbstractAnalyzerTest<ChainedAnalyzer>
{
    public ChainedAnalyzerTest()
    {
        super(new ChainedAnalyzer());
    }

    @Test
    public void testApply()
    {
        analyzer = new ChainedAnalyzer(new QGramTokenizer(3), new QGramTokenizer(2));
        Object[] result = analyzer.apply("abcd").toArray();
        assertEquals("ab", result[0]);
        assertEquals("bc", result[1]);
        assertEquals("bc", result[2]);
        assertEquals("cd", result[3]);
    }
}
