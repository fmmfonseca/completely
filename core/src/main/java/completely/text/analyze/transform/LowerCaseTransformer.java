package completely.text.analyze.transform;

import completely.text.analyze.Analyzer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Convert text into lowercase.
 */
public class LowerCaseTransformer extends Analyzer
{
    private final Locale locale;

    public LowerCaseTransformer()
    {
        this(Locale.getDefault());
    }

    public LowerCaseTransformer(Locale locale)
    {
        this.locale = locale;
    }

    @Override
    public Collection<String> apply(Collection<String> input)
    {
        List<String> result = new LinkedList<String>();
        for (String text : input)
        {
            result.add(text.toLowerCase(locale));
        }
        return result;
    }
}
