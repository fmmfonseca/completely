package completely;

import completely.data.Indexable;

import java.util.Arrays;
import java.util.List;

/**
 * This record implements equals/hashCode, which means 2 instances with the same values won't be found twice, only 1 of them.
 */
class TestRecordImplementingEqualsHashcode implements Indexable
{
    private final List<String> fields;
    private final double score;

    TestRecordImplementingEqualsHashcode(double score, String... fields)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestRecordImplementingEqualsHashcode that = (TestRecordImplementingEqualsHashcode) o;

        if (Double.compare(that.score, score) != 0) return false;
        if (fields != null ? !fields.equals(that.fields) : that.fields != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = fields != null ? fields.hashCode() : 0;
        temp = Double.doubleToLongBits(score);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
