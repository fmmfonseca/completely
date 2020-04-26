package com.miguelfonseca.completely.text.analyze.transform;

import com.miguelfonseca.completely.text.analyze.AbstractAnalyzerTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiacriticsTransformerTest extends AbstractAnalyzerTest<DiacriticsTransformer>
{
    public DiacriticsTransformerTest()
    {
        super(new DiacriticsTransformer());
    }

    @Test
    public void testApply()
    {
        Object[] result = analyzer.apply("àbç").toArray();
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
