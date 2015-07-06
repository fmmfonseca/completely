package completely.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * An implementation of {@link Indexable} that contains (possibly) multiple strings, and a score value.
 */
public class MultiStringsWithScoreRecord implements Indexable {

    private final List<String> strings;
    private final double score;

    public MultiStringsWithScoreRecord(double score, String ... strings) {
        this(score, Arrays.asList(strings));
    }
    public MultiStringsWithScoreRecord(double score, List<String> strings) {
        this.strings = strings;
        this.score = score;
    }

    @Override
    public List<String> getFields() {
        return strings;
    }

    @Override
    public double getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "MultiStringsWithScoreRecord{" +
                "strings=" + strings +
                ", score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MultiStringsWithScoreRecord that = (MultiStringsWithScoreRecord) o;

        if (Double.compare(that.score, score) != 0) return false;
        if (strings != null ? !strings.equals(that.strings) : that.strings != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = strings != null ? strings.hashCode() : 0;
        temp = Double.doubleToLongBits(score);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
