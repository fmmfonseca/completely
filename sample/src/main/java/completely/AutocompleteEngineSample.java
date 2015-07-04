package completely;

import completely.data.SingleStringRecord;
import completely.text.analyze.transform.LowerCaseTransformer;
import completely.text.index.FuzzyIndex;
import completely.text.index.PatriciaTrie;
import completely.text.match.EditDistanceAutomaton;

import java.io.Console;
import java.util.Collection;

public final class AutocompleteEngineSample
{
    @SuppressWarnings("checkstyle:leftcurly")
    private AutocompleteEngineSample() { }

    public static void main(String[] args)
    {
        AutocompleteEngine<SingleStringRecord> engine = new AutocompleteEngine.Builder<SingleStringRecord>()
            .setIndex(new SampleAdapter())
            .setAnalyzer(new LowerCaseTransformer())
            .build();

        String[] entries = {
            "ActionScript",
            "AppleScript",
            "Asp",
            "BASIC",
            "C",
            "C++",
            "Clojure",
            "COBOL",
            "ColdFusion",
            "Erlang",
            "Fortran",
            "Groovy",
            "Haskell",
            "Java",
            "JavaScript",
            "Lisp",
            "Perl",
            "PHP",
            "Python",
            "Ruby",
            "Scala",
            "Scheme",
        };
        for (String entry : entries)
        {
            engine.add(new SingleStringRecord(entry));
        }

        Console console = System.console();
        while (true)
        {
            String input = console.readLine("Query: ");
            for (SingleStringRecord record : engine.search(input))
            {
                console.printf("- %s%n", record);
            }
        }
    }

    private static class SampleAdapter implements IndexAdapter<SingleStringRecord>
    {
        private FuzzyIndex<SingleStringRecord> index = new PatriciaTrie<SingleStringRecord>();

        @Override
        public Collection<SingleStringRecord> get(String token)
        {
            return index.getAny(new EditDistanceAutomaton(token, Math.log(token.length())));
        }

        @Override
        public boolean put(String token, SingleStringRecord value)
        {
            return index.put(token, value);
        }
    }

}
