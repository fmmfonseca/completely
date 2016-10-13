package com.miguelfonseca.completely;

import com.miguelfonseca.completely.data.SampleRecord;
import com.miguelfonseca.completely.data.ScoredObject;
import com.miguelfonseca.completely.text.index.FuzzyIndex;
import com.miguelfonseca.completely.text.index.PatriciaTrie;
import com.miguelfonseca.completely.text.match.EditDistanceAutomaton;

import java.util.Collection;

public class SampleAdapter implements IndexAdapter<SampleRecord>
{
    private FuzzyIndex<SampleRecord> index = new PatriciaTrie<>();

    @Override
    public Collection<ScoredObject<SampleRecord>> get(String token)
    {
        double threshold = Math.log(token.length() - 1);
        return index.getAny(new EditDistanceAutomaton(token, threshold));
    }

    @Override
    public boolean put(String token, SampleRecord value)
    {
        return index.put(token, value);
    }

    @Override
    public boolean remove(SampleRecord value)
    {
        return index.remove(value);
    }
}
