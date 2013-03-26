package completely.text.analyze.transform;

import completely.text.analyze.Analyzer;

import java.text.Normalizer;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Strip text diacritics.
 */
public class DiacriticsTransformer extends Analyzer
{
    @Override
    public Collection<String> apply(Collection<String> input)
    {
        List<String> result = new LinkedList<String>();
        for (String text : input)
        {
            StringBuilder builder = new StringBuilder();
            String canonical = Normalizer.normalize(text, Normalizer.Form.NFD);
            for (int i = 0; i < canonical.length(); ++i)
            {
                if (Character.getType(canonical.charAt(i)) != Character.NON_SPACING_MARK)
                {
                    builder.append(canonical.charAt(i));
                }
            }
            result.add(builder.toString());
        }
        return result;
    }
}
