package completely;

import completely.data.SampleRecord;
import completely.data.ScoredObject;
import completely.text.index.FuzzyIndex;
import completely.text.index.PatriciaTrie;
import completely.text.match.EditDistanceAutomaton;

import java.util.Collection;

public class SampleAdapter implements IndexAdapter<SampleRecord>
{
    private FuzzyIndex<SampleRecord> index = new PatriciaTrie<SampleRecord>();

    @Override
    public Collection<ScoredObject<SampleRecord>> get(String token)
    {
        return index.getAny(new EditDistanceAutomaton(token, Math.log(token.length())));
    }

    @Override
    public boolean put(String token, SampleRecord value)
    {
        return index.put(token, value);
    }
}
