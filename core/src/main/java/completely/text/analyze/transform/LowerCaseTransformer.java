package completely.text.analyze.transform;

import completely.text.analyze.Analyzer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static completely.common.Precondition.checkPointer;

/**
 * Convert text into lowercase.
 */
public class LowerCaseTransformer extends Analyzer
{
    private final Locale locale;

    /**
     * Constructs a new {@link LowerCaseTransformer}.
     */
    public LowerCaseTransformer()
    {
        this(Locale.getDefault());
    }

    /**
     * Constructs a new {@link LowerCaseTransformer}.
     */
    public LowerCaseTransformer(Locale locale)
    {
        checkPointer(locale != null);
        this.locale = locale;
    }

    @Override
    public Collection<String> apply(Collection<String> input)
    {
        checkPointer(input != null);
        List<String> result = new LinkedList<>();
        for (String text : input)
        {
            checkPointer(text != null);
            result.add(text.toLowerCase(locale));
        }
        return result;
    }

    @Override
    public String toString() {
        return "LowerCaseTransformer{" +
                "locale=" + locale +
                '}';
    }
}
