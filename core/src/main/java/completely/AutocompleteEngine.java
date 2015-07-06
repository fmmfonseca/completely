package completely;

import completely.data.Indexable;
import completely.text.analyze.Analyzer;
import completely.text.analyze.NoopAnalyzer;
import completely.text.index.Index;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.Nullable;

import static completely.common.Precondition.checkPointer;

/**
 * Facade for indexing and searching {@link Indexable} elements.
 *
 * <p>To create one, do something like this:
 * <pre><code>
 * AutocompleteEngine<MyRecord> engine new AutocompleteEngine.Builder<MyRecord>()
 *     .setIndex(new HashMultiMap<>())
 *     // ... more config
 *     .build();
 * </code></pre></p>
 */
public final class AutocompleteEngine<T extends Indexable>
{
    private final Analyzer analyzer;
    private final Comparator<T> comparator;
    private final IndexAdapter<T> index;
    private final Lock read;
    private final Lock write;

    private AutocompleteEngine(Builder<T> builder)
    {
        assert builder != null;
        this.analyzer = builder.analyzer;
        this.comparator = builder.comparator;
        this.index = builder.index;
        ReadWriteLock lock = new ReentrantReadWriteLock();
        this.read = lock.readLock();
        this.write = lock.writeLock();
    }

    /**
     * Indexes a single element.
     *
     * @see #addAll
     * @throws NullPointerException if {@code element} is null;
     */
    public boolean add(T element)
    {
        return addAll(Arrays.asList(element));
    }

    /**
     * Indexes a collection of elements.
     *
     * TODO define what happens with elements that were indexed already.
     *
     * @throws NullPointerException if {@code elements} is null or contains a null element;
     */
    public boolean addAll(Collection<T> elements)
    {
        checkPointer(elements != null);
        boolean result = false;
        for (T element : elements)
        {
            checkPointer(element != null);
            write.lock();
            try
            {
                for (String field : element.getFields())
                {
                    for (String token : analyzer.apply(field))
                    {
                        if (index.put(token, element))
                        {
                            result = true;
                        }
                    }
                }
            }
            finally
            {
                write.unlock();
            }
        }
        return result;
    }

    /**
     * Returns a {@link List} of all elements that match the {@code query}, sorted
     * according to the default comparator, or unsorted if there is no default comparator.
     *
     * @throws java.lang.IllegalArgumentException on null or empty {@code query}
     */
    public List<T> search(String query)
    {
        return search(query, comparator);
    }

    /**
     * Returns a {@link List} of all elements that match a query, sorted
     * according to the specified comparator.
     *
     * @throws java.lang.IllegalArgumentException on null or empty {@code query}
     */
    public List<T> search(String query, @Nullable Comparator<T> comparator)
    {
        if (query==null) throw new IllegalArgumentException("Null query not permitted, useless!");
        if (query.isEmpty()) throw new IllegalArgumentException("Empty query not permitted, useless!");

        read.lock();
        try
        {
            Iterator<String> tokens = analyzer.apply(query).iterator();
            if (!tokens.hasNext()) {
                //only garbage input.
                return Collections.emptyList();
            }

            //this search implementation requires that a hit matches all tokens.

            List<T> result = new ArrayList<>();
            result.addAll(index.get(tokens.next()));
            if (result.isEmpty()) {
                //nothing found for first token. can't improve from here.
                return Collections.emptyList();
            }
            while (tokens.hasNext())
            {
                result.retainAll(index.get(tokens.next()));
                if (result.isEmpty()) {
                    //nothing matches all tokens so far. can't improve from here.
                    return Collections.emptyList();
                }
            }
            if (comparator != null)
            {
                Collections.sort(result, comparator);
            }
            return result;
        }
        finally
        {
            read.unlock();
        }
    }

    /**
     * Returns a {@link List} of the top elements that match a query.
     *
     * @throws java.lang.IllegalArgumentException on null or empty {@code query}
     */
    public List<T> search(String query, int limit)
    {
        return search(query, comparator, limit);
    }

    /**
     * Returns a {@link List} of the top elements that match a query, sorted
     * according to the specified comparator.
     *
     * @throws java.lang.IllegalArgumentException on null or empty {@code query}
     */
    public List<T> search(String query, @Nullable Comparator<T> comparator, int limit)
    {
        List<T> result = search(query, comparator);
        if (result.size() > limit)
        {
            return result.subList(0, limit);
        }
        return result;
    }

    /**
     * Builder for constructing {@link AutocompleteEngine} instances.
     */
    public static class Builder<T extends Indexable>
    {
        private Analyzer analyzer = NoopAnalyzer.getInstance();
        private Comparator<T> comparator;
        private IndexAdapter<T> index;

        /**
         * Constructs a new {@link AutocompleteEngine.Builder}.
         */
        public Builder()
        {
        }

        /**
         * Set the analyzer.
         */
        public Builder<T> setAnalyzer(Analyzer analyzer)
        {
            this.analyzer = analyzer;
            return this;
        }

        /**
         * Set the comparator.
         */
        public Builder<T> setComparator(@Nullable Comparator<T> comparator)
        {
            this.comparator = comparator;
            return this;
        }

        /**
         * Set the index.
         *
         * @throws NullPointerException if {@code index} is null;
         */
        public Builder<T> setIndex(final Index<T> index)
        {
            checkPointer(index != null);
            return setIndex(new IndexAdapter<T>()
            {
                @Override
                public Collection<T> get(String token)
                {
                    return index.getAll(token);
                }

                @Override
                public boolean put(String token, T value)
                {
                    return index.put(token, value);
                }
            });
        }

        /**
         * Set the index.
         */
        public Builder<T> setIndex(IndexAdapter<T> index)
        {
            this.index = index;
            return this;
        }

        /**
         * Returns a new {@link AutocompleteEngine} parameterized according to
         * the builder.
         *
         * @throws NullPointerException if {@code analyzer} or {@code index} are null;
         */
        public AutocompleteEngine<T> build()
        {
            checkPointer(analyzer != null);
            checkPointer(index != null);
            return new AutocompleteEngine<>(this);
        }
    }
}
