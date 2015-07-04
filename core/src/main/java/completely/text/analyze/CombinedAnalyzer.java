package completely.text.analyze;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Combines multiple analyzers and runs one by one in the given order.
 *
 * <p>Example use:
 * new CombinedAnalyzer(new LowerCaseTransformer(), new DiacriticsTransformer())
 * </p>
 */
public class CombinedAnalyzer extends Analyzer {

    private final List<Analyzer> analyzers;

    public CombinedAnalyzer(Analyzer ... analyzers) {
        this(Arrays.asList(analyzers));
    }
    public CombinedAnalyzer(List<Analyzer> analyzers) {
        this.analyzers = analyzers;
    }


    @Override
    public Collection<String> apply(Collection<String> input) {
        Collection<String> coll = input;
        for (Analyzer analyzer : analyzers) {
            coll = analyzer.apply(coll);
        }
        return coll;
    }

    @Override
    public String toString() {
        return "CombinedAnalyzer{" +
                "analyzers=" + analyzers +
                '}';
    }
}
