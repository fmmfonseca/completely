package completely.text.analyze.tokenize;

import completely.text.analyze.Analyzer;

import java.text.BreakIterator;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Break text into words.
 */
public class WordTokenizer extends Analyzer
{
    private final BreakIterator boundary;

    public WordTokenizer()
    {
        this.boundary = BreakIterator.getWordInstance();
    }

    @Override
    public Collection<String> apply(Collection<String> input)
    {
        List<String> result = new LinkedList<String>();
        for (String text : input)
        {
            boundary.setText(text.toString());
            for (int start = boundary.first(), end = boundary.next(); end != BreakIterator.DONE; start = end, end = boundary.next())
            {
                String word = text.substring(start, end);
                if (Character.isLetterOrDigit(word.charAt(0)))
                {
                    result.add(word);
                }
            }
        }
        return result;
    }
}
