package completely.sort;

import completely.data.Indexable;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class SorterUtil {

    /**
     * This can be used after {@link completely.AutocompleteEngine#search} and then {@link completely.sort.RecordSorter#sort}
     * to have a simple, sorted list of records.
     */
    public static <T extends Indexable> List<T> extractRecords(List<WeightedRecord<T>> weighted) {
        List<T> ret = new ArrayList<>(weighted.size());
        for (WeightedRecord<T> tWeightedRecord : weighted) {
            ret.add(tWeightedRecord.getRecord());
        }
        return ret;
    }

}
