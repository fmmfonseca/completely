package completely.sort;

import completely.data.Indexable;

/**
 * Computes the importance of a found {@link Indexable} record after a search.
 * This importance (weight) can then be put in relation to the others, using the {@link completely.sort.RecordSorter},
 * to sort the results.
 *
 * This is more flexible compared to the built-in Comparator logic in the {@link completely.AutocompleteEngine}.
 * For one, because you are given the {@code query} string. And for the other, because it may be easier to compute
 * a score number for one record alone in isolated view than to compare one record with another one.
 */
public interface RecordWeighter<T extends Indexable> {

    /**
     * @param query The query exactly as it was used in the AutocompleteEngine.search().
     * @return The higher the more important this match is.
     */
    double weight(String query, T record);

}
