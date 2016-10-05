package com.miguelfonseca.completely.text.analyze;

import java.util.Collection;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public abstract class AbstractAnalyzerTest<T extends Analyzer>
{
    @Rule
    @SuppressWarnings("checkstyle:visibilitymodifier")
    public ExpectedException exceptionRule;

    protected Analyzer analyzer;

    public AbstractAnalyzerTest(T analyzer)
    {
        this.exceptionRule = ExpectedException.none();
        this.analyzer = analyzer;
    }

    @Test
    public void testApplyNull()
    {
        exceptionRule.expect(NullPointerException.class);
        analyzer.apply((Collection<String>) null);
    }
}
