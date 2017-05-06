package com.miguelfonseca.completely.text.analyze;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static com.miguelfonseca.completely.common.Precondition.checkPointer;

/**
 * Chain of multiple {@link Analyzer} functions.
 */
public class ChainedAnalyzer extends Analyzer
{
    private Collection<Analyzer> chain;

    /**
     * Constructs a new {@link ChainedAnalyzer}.
     */
    public ChainedAnalyzer(Analyzer... chain)
    {
        this.chain = Arrays.asList(chain);
    }

    @Override
    public Collection<String> apply(Collection<String> input)
    {
        checkPointer(input != null);
        Collection<String> result = new ArrayList<>(input);
        for (Analyzer analyzer : chain)
        {
            checkPointer(analyzer != null);
            result = analyzer.apply(result);
        }
        return result;
    }
}
