package com.miguelfonseca.completely.text.analyze;

import java.util.Collection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class AbstractAnalyzerTest<T extends Analyzer>
{
    protected Analyzer analyzer;

    public AbstractAnalyzerTest(T analyzer)
    {
        this.analyzer = analyzer;
    }

    @Test
    public void testApplyNull()
    {
        assertThrows(
            NullPointerException.class,
            () -> analyzer.apply((Collection<String>) null)
        );
    }
}
