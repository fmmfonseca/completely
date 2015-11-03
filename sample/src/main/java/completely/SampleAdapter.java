package completely;

import completely.data.SampleRecord;
import completely.data.ScoredObject;
import completely.text.index.FuzzyIndex;
import completely.text.index.PatriciaTrie;
import completely.text.match.EditDistanceAutomaton;

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
}
