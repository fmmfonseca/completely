package completely.text.analyze;

import java.util.Collection;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public abstract class AnalyzerTest<T extends Analyzer>
{
    @Rule
    public ExpectedException exceptionRule;

    protected Analyzer analyzer;

    public AnalyzerTest(T analyzer)
    {
        this.exceptionRule = ExpectedException.none();
        this.analyzer = analyzer;
    }

    @Test
    public void testApplyNull()
    {
        exceptionRule.expect(NullPointerException.class);
        analyzer.apply((Collection) null);
    }
}
