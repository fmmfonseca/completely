package completely.sort;

import completely.data.Indexable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Sorts {@link completely.data.Indexable} records from better to worse based on a {@link completely.sort.RecordWeighter}.
 * Use this on the result that you get from {@link completely.AutocompleteEngine#search}.
 */
public class RecordSorter<T extends Indexable> {

    private final RecordWeighter<T> weighter;

    public RecordSorter(RecordWeighter<T> weighter) {
        this.weighter = weighter;
    }


    /**
     * @param query The query exactly as it was used in the AutocompleteEngine.search().
     * @param unsorted The result from the AutocompleteEngine.search().
     * @return Sorted from better to worse.
     */
    public List<WeightedRecord<T>> sort(String query, List<T> unsorted) {
        List<WeightedRecord<T>> weighted = new ArrayList<>(unsorted.size());
        for (T t : unsorted) {
            WeightedRecord<T> weightedRecord = new WeightedRecord<>(weighter.weight(query, t), t);
            weighted.add(weightedRecord);
        }
        Collections.sort(weighted);
        return weighted;
    }

}
