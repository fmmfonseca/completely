package com.miguelfonseca.completely.text.analyze.tokenize;

import com.miguelfonseca.completely.text.analyze.AbstractAnalyzerTest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WordTokenizerTest extends AbstractAnalyzerTest<WordTokenizer>
{
    public WordTokenizerTest()
    {
        super(new WordTokenizer());
    }

    @Test
    public void testApply()
    {
        Object[] result = analyzer.apply("Lorem ipsum, dolor sit amet.").toArray();
        assertEquals(5, result.length);
        assertEquals("Lorem", result[0]);
        assertEquals("ipsum", result[1]);
        assertEquals("dolor", result[2]);
        assertEquals("sit", result[3]);
        assertEquals("amet", result[4]);
    }

    @Test
    public void testApplyNullElement()
    {
        exceptionRule.expect(NullPointerException.class);
        analyzer.apply((String) null);
    }
}
