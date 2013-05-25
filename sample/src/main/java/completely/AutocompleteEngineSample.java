package completely;

import completely.data.Indexable;
import completely.text.analyze.transform.LowerCaseTransformer;
import completely.text.index.FuzzyIndex;
import completely.text.index.HashTrie;
import completely.text.match.EditDistanceAutomaton;

import java.io.Console;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public final class AutocompleteEngineSample
{
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
        private FuzzyIndex<SampleRecord> index = new HashTrie<SampleRecord>();

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

    private static class SampleRecord implements Indexable
    {
        private final String name;

        SampleRecord(String name)
        {
            this.name = name;
        }

        @Override
        public List<String> getFields()
        {
            return Arrays.asList(name);
        }

        public String getName()
        {
            return name;
        }

        @Override
        public double getScore()
        {
            return 0;
        }
    }
}
