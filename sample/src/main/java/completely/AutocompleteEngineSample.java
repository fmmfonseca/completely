package completely;

import completely.data.SampleRecord;
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
        AutocompleteEngine<SampleRecord> engine = new AutocompleteEngine.Builder<SampleRecord>()
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
            engine.add(new SampleRecord(entry));
        }

        Console console = System.console();
        while (true)
        {
            String input = console.readLine("Query: ");
            for (SampleRecord record : engine.search(input))
            {
                console.printf("- %s%n", record.getName());
            }
        }
    }

    private static class SampleAdapter implements IndexAdapter<SampleRecord>
    {
        private FuzzyIndex<SampleRecord> index = new PatriciaTrie<SampleRecord>();

        @Override
        public Collection<SampleRecord> get(String token)
        {
            return index.getAny(new EditDistanceAutomaton(token, Math.log(token.length())));
        }

        @Override
        public boolean put(String token, SampleRecord value)
        {
            return index.put(token, value);
        }
    }
}
