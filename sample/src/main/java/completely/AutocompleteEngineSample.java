package completely;

import completely.data.SimpleRecord;
import completely.text.analyze.transform.LowerCaseTransformer;
import completely.text.index.HashTrie;

import java.io.Console;

public final class AutocompleteEngineSample
{
    public static void main(String[] args)
    {
        AutocompleteEngine<SimpleRecord> engine = new AutocompleteEngine.Builder<SimpleRecord>()
            .setIndex(new HashTrie<SimpleRecord>())
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
            engine.add(new SimpleRecord(entry));
        }

        Console console = System.console();
        while (true)
        {
            String input = console.readLine("Query: ");
            for (SimpleRecord record : engine.search(input))
            {
                console.printf("- %s%n", record.getText());
            }
        }
    }
}
