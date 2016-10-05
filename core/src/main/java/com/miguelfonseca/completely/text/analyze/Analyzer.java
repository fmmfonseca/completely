package com.miguelfonseca.completely.text.analyze;

import java.util.Arrays;
import java.util.Collection;

/**
 * Text function.
 */
public abstract class Analyzer
{
    /**
     * Applies the function.
     */
    public Collection<String> apply(String... input)
    {
        return apply(Arrays.asList(input));
    }

    /**
     * Applies the function.
     *
     * @throws NullPointerException if {@code input} is null;
     */
    public abstract Collection<String> apply(Collection<String> input);
}
