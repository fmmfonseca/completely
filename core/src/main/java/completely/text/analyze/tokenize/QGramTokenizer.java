package completely.text.analyze.tokenize;

import completely.text.analyze.Analyzer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static completely.common.Precondition.checkPointer;

/**
 * Break text into q-grams (also known as n-grams).
 */
public class QGramTokenizer extends Analyzer
{
    private final int size;

    /**
     * Constructs a new {@link QGramTokenizer}.
     */
    public QGramTokenizer(int size)
    {
        this.size = size;
    }

    @Override
    public Collection<String> apply(Collection<String> input)
    {
        checkPointer(input != null);
        List<String> result = new LinkedList<>();
        for (String text : input)
        {
            checkPointer(text != null);
            for (int i = 0; i + size <= text.length(); ++i)
            {
                result.add(text.substring(i, i + size));
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "QGramTokenizer{" +
                "size=" + size +
                '}';
    }
}
