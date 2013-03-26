package completely.text.analyze;

import java.util.Arrays;
import java.util.Collection;

/**
 * Text function.
 */
public abstract class Analyzer
{
    public Collection<String> apply(String... input)
    {
        return apply(Arrays.asList(input));
    }

    public abstract Collection<String> apply(Collection<String> input);
}
