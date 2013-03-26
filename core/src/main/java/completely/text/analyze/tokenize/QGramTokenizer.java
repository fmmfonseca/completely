package completely.text.analyze.tokenize;

import completely.text.analyze.Analyzer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Break text into q-grams (also known as n-grams).
 */
public class QGramTokenizer extends Analyzer
{
    private final int size;

    public QGramTokenizer(int size)
    {
        this.size = size;
    }

    @Override
    public Collection<String> apply(Collection<String> input)
    {
        List<String> result = new LinkedList<String>();
        for (String text : input)
        {
            for (int i = 0; i + size <= text.length(); ++i)
            {
                result.add(text.substring(i, i + size));
            }
        }
        return result;
    }
}
