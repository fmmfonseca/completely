package completely.text.analyze.filter;

import completely.text.analyze.Analyzer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static completely.common.Precondition.checkPointer;

/**
 * Exclude null text.
 */
public class NullFilter extends Analyzer
{
    @Override
    public Collection<String> apply(Collection<String> input)
    {
        checkPointer(input != null);
        List<String> result = new LinkedList<>();
        for (String text : input)
        {
            if (text != null)
            {
                result.add(text);
            }
        }
        return result;
    }
}
