package completely.sort;

import completely.data.Indexable;

/**
 * A record that was found by a search, with a weight score for result sorting.
 */
public class WeightedRecord<T extends Indexable> implements Comparable<WeightedRecord> {

    private final double score;
    private final T record;

    public WeightedRecord(double score, T record) {
        this.score = score;
        this.record = record;
    }

    /**
     * The higher the better.
     */
    public double getScore() {
        return score;
    }

    public T getRecord() {
        return record;
    }

    /**
     * The one with higher score comes first.
     * Records with the same score return a zero... beware to not use this in a TreeMap.
     */
    @Override
    public int compareTo(WeightedRecord o) {
        return Double.compare(o.score, score);
    }


    @Override
    public String toString() {
        return "WeightedRecord{" +
                "score=" + score +
                ", record=" + record +
                '}';
    }
}
