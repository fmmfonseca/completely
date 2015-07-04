package completely.text.analyze;

import java.util.Collection;

/**
 * Implementation that does nothing, and passes the input back.
 */
public class NoopAnalyzer extends Analyzer {

    private static final NoopAnalyzer INSTANCE = new NoopAnalyzer();

    public static NoopAnalyzer getInstance() {
        return INSTANCE;
    }

    private NoopAnalyzer() {
    }


    @Override
    public Collection<String> apply(Collection<String> input) {
        return input;
    }

}
