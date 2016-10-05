package com.miguelfonseca.completely.text.analyze.filter;

import com.miguelfonseca.completely.text.analyze.AbstractAnalyzerTest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("checkstyle:multiplestringliterals")
public class NullFilterTest extends AbstractAnalyzerTest<NullFilter>
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
