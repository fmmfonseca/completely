package completely.data;

import java.util.Collections;
import java.util.List;

/**
 * An implementation of {@link Indexable} that contains just one string field and a score value.
 */
public class SingleStringWithScoreRecord implements Indexable {

    private final String string;
    private final double score;

    public SingleStringWithScoreRecord(String string, double score) {
        this.string = string;
        this.score = score;
    }

    @Override
    public List<String> getFields() {
        return Collections.singletonList(string);
    }

    @Override
    public double getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "SingleStringWithScoreRecord{" +
                "string='" + string + '\'' +
                ", score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SingleStringWithScoreRecord that = (SingleStringWithScoreRecord) o;

        if (Double.compare(that.score, score) != 0) return false;
        if (string != null ? !string.equals(that.string) : that.string != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = string != null ? string.hashCode() : 0;
        temp = Double.doubleToLongBits(score);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
