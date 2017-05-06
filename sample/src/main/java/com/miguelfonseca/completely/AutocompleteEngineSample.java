package com.miguelfonseca.completely;

import com.miguelfonseca.completely.data.SampleRecord;
import com.miguelfonseca.completely.text.analyze.tokenize.WordTokenizer;
import com.miguelfonseca.completely.text.analyze.transform.LowerCaseTransformer;

import java.io.Console;
import java.util.Locale;

public final class AutocompleteEngineSample
{
    @SuppressWarnings("checkstyle:leftcurly")
    private AutocompleteEngineSample() { }

    public static void main(String[] args)
    {
        AutocompleteEngine<SampleRecord> engine = new AutocompleteEngine.Builder<SampleRecord>()
            .setIndex(new SampleAdapter())
            .setAnalyzers(new LowerCaseTransformer(), new WordTokenizer())
            .build();

        for (String country : Locale.getISOCountries())
        {
            Locale locale = new Locale("", country);
            engine.add(new SampleRecord(locale.getDisplayCountry()));
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
}
