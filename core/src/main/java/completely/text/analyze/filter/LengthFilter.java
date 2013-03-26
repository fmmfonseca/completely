package completely.text.analyze.filter;

import completely.text.analyze.Analyzer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Exclude text with length outside boundaries.
 */
public class LengthFilter extends Analyzer
{
    private final int min, max;

    public LengthFilter(int min, int max)
    {
        this.min = min;
        this.max = max;
    }

    @Override
    public Collection<String> apply(Collection<String> input)
    {
        List<String> result = new LinkedList<String>();
        for (String text : input)
        {
            if (text.length() >= min && text.length() <= max)
            {
                result.add(text);
            }
        }
        return result;
    }
}
