package com.miguelfonseca.completely.text.analyze;

import com.miguelfonseca.completely.text.analyze.tokenize.WordTokenizer;
import com.miguelfonseca.completely.text.analyze.transform.LowerCaseTransformer;

import java.util.Collection;

public class SampleAnalyzer extends Analyzer
{
    private Analyzer tokenizer = new WordTokenizer();
    private Analyzer transformer = new LowerCaseTransformer();

    @Override
    public Collection<String> apply(Collection<String> input)
    {
        return tokenizer.apply(transformer.apply(input));
    }
}
