package com.miguelfonseca.completely.text.analyze.transform;

import com.miguelfonseca.completely.text.analyze.AbstractAnalyzerTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LowerCaseTransformerTest extends AbstractAnalyzerTest<LowerCaseTransformer>
{
    public LowerCaseTransformerTest()
    {
        super(new LowerCaseTransformer());
    }

    @Test
    public void testCreateNullLocale()
    {
        assertThrows(
            NullPointerException.class,
            () -> new LowerCaseTransformer(null)
        );
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
        assertThrows(
            NullPointerException.class,
            () -> analyzer.apply((String) null)
        );
    }
}
