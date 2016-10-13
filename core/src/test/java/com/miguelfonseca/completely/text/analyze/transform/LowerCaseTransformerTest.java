package com.miguelfonseca.completely.text.analyze.transform;

import com.miguelfonseca.completely.text.analyze.AbstractAnalyzerTest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LowerCaseTransformerTest extends AbstractAnalyzerTest<LowerCaseTransformer>
{
    public LowerCaseTransformerTest()
    {
        super(new LowerCaseTransformer());
    }

    @Test
    public void testCreateNullLocale()
    {
        exceptionRule.expect(NullPointerException.class);
        new LowerCaseTransformer(null);
    }

    @Test
    public void testApply()
    {
        Object[] result = analyzer.apply("AbC").toArray();
        assertEquals("abc", result[0]);
    }


    @Test
    public void testApplyNullElement()
    {
        exceptionRule.expect(NullPointerException.class);
        analyzer.apply((String) null);
    }
}
