package completely;

import completely.data.Indexable;
import completely.text.analyze.transform.LowerCaseTransformer;
import completely.text.index.HashTrie;

import java.io.Console;

public final class AutocompleteEngineSample
{
    public static void main(String[] args)
    {
        AutocompleteEngine<SampleRecord> engine = new AutocompleteEngine.Builder<SampleRecord>()
            .setIndex(new HashTrie<SampleRecord>())
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
            "Scheme"
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
                console.printf("- %s%n", record.getText());
            }
        }
    }

    private static class SampleRecord implements Indexable
    {
        private final String text;

        SampleRecord(String text)
        {
            this.text = text;
        }

        @Override
        public String getText()
        {
            return text;
        }

        @Override
        public double getScore()
        {
            return 0;
        }
    }
}
