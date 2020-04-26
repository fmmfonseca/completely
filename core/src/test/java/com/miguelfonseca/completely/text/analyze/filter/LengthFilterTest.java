package com.miguelfonseca.completely.text.analyze.filter;

import com.miguelfonseca.completely.text.analyze.AbstractAnalyzerTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("checkstyle:multiplestringliterals")
public class LengthFilterTest extends AbstractAnalyzerTest<LengthFilter>
{
    public LengthFilterTest()
    {
        super(new LengthFilter(2, 3));
    }

    @Test
    public void testApply()
    {
        Object[] result = analyzer.apply("a", "aa", "aaa", "aaaa").toArray();
        assertEquals(2, result.length);
        assertEquals("aa", result[0]);
        assertEquals("aaa", result[1]);
    }

    @Test
    public void testApplyNullElement()
    {
        assertThrows(
            NullPointerException.class,
            () -> analyzer.apply((String) null)
        );
    }
}
