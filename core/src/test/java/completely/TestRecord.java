package completely;

import completely.data.Indexable;

import java.util.Arrays;
import java.util.List;

/**
*
*/
class TestRecord implements Indexable
{
    private final List<String> fields;
    private final double score;

    TestRecord(double score, String... fields)
    {
        this.fields = Arrays.asList(fields);
        this.score = score;
    }

    @Override
    public List<String> getFields()
    {
        return fields;
    }

    @Override
    public double getScore()
    {
        return score;
    }
}
