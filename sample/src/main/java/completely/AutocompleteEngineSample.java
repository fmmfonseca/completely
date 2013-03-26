package completely;

import completely.data.Record;
import completely.data.SimpleRecord;
import completely.text.analyze.Analyzer;
import completely.text.analyze.transform.LowerCaseTransformer;
import completely.text.index.FuzzyIndex;
import completely.text.index.HashTrie;

import java.io.Console;

public final class AutocompleteEngineSample
{
    public static void main(String[] args)
    {
        FuzzyIndex<Record> index = new HashTrie<Record>();
        Analyzer analyzer = new LowerCaseTransformer();
        AutocompleteEngine<Record> engine = new AutocompleteEngine<Record>(index, analyzer);

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
            for (Record record : engine.search(input))
            {
                console.printf("- %s%n", record.getText());
            }
        }
    }
}
