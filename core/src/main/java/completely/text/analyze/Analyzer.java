package completely.text.analyze;

import java.util.Arrays;
import java.util.Collection;

/**
 * Text function.
 */
public abstract class Analyzer
{
    /**
     * Overloaded convenience method, for detail see {@link #apply(java.util.Collection)}.
     */
    public Collection<String> apply(String... input)
    {
        return apply(Arrays.asList(input));
    }

    /**
     * Applies the function.
     *
     * The method is permitted to convert items, to add items and to remove items, and it is not required to
     * perform anything.
     *
     * @param input may not be modified in this method.
     * @return Possibly immutable, and anyway it may not be modified.
     *         Possibly the same instance as the input if nothing was modified.
     *         Possibly empty.
     * @throws NullPointerException if {@code input} is null;
     */
    public abstract Collection<String> apply(Collection<String> input);
}
