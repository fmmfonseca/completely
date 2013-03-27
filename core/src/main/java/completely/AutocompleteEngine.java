package completely;

import completely.data.Indexable;
import completely.text.analyze.Analyzer;
import completely.text.index.FuzzyIndex;
import completely.text.index.Index;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Facade for indexing and searching {@link Indexable} elements.
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
        this.analyzer = builder.analyzer;
        this.comparator = builder.comparator;
        this.index = builder.index;
        ReadWriteLock lock = new ReentrantReadWriteLock();
        this.read = lock.readLock();
        this.write = lock.writeLock();
    }

    /**
     * Indexes a single element.
     */
    public boolean add(T element)
    {
        write.lock();
        try
        {
            for (String token : analyzer.apply(element.getText()))
            {
                index.put(token, element);
            }
            return true;
        }
        finally
        {
            write.unlock();
        }
    }

    /**
     * Indexes multiple elements.
     */
    public boolean add(Iterable<T> elements)
    {
        for (T element : elements)
        {
            add(element);
        }
        return true;
    }

    /**
     * Returns a {@link List} of all elements that match a query, sorted
     * according to the default comparator.
     */
    public List<T> search(String query)
    {
        return search(query, comparator);
    }

    /**
     * Returns a {@link List} of all elements that match a query, sorted
     * according to the specified comparator.
     */
    public List<T> search(String query, Comparator<T> comparator)
    {
        read.lock();
        try
        {
            List<T> result = new ArrayList<T>();
            Iterator<String> tokens = analyzer.apply(query).iterator();
            if (tokens.hasNext())
            {
                result.addAll(index.get(tokens.next()));
            }
            while (tokens.hasNext())
            {
                result.retainAll(index.get(tokens.next()));
            }
            Collections.sort(result, comparator);
            return result;
        }
        finally
        {
            read.unlock();
        }
    }

    /**
     * Returns a {@link List} of the top elements that match a query.
     */
    public List<T> search(String query, int limit)
    {
        return search(query, comparator, limit);
    }

    /**
     * Returns a {@link List} of the top elements that match a query, sorted
     * according to the specified comparator.
     */
    public List<T> search(String query, Comparator<T> comparator, int limit)
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
        private Analyzer analyzer;
        private Comparator<T> comparator;
        private IndexAdapter<T> index;

        public Builder()
        {
            this.analyzer = new Analyzer()
            {
                @Override
                public Collection<String> apply(Collection<String> input)
                {
                    return new ArrayList<String>(input);
                }
            };
            this.comparator = new Comparator<T>()
            {
                @Override
                public int compare(T o1, T o2)
                {
                    Double score1 = o1.getScore();
                    Double score2 = o2.getScore();
                    return score2.compareTo(score1);
                }
            };
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
        public Builder<T> setComparator(Comparator<T> comparator)
        {
            this.comparator = comparator;
            return this;
        }

        /**
         * Set the index.
         */
        public Builder<T> setIndex(final Index<T> index)
        {
            return setIndex(new IndexAdapter<T>()
            {
                @Override
                public Collection<T> get(String token)
                {
                    return index.get(token);
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
        public Builder<T> setIndex(final FuzzyIndex<T> index)
        {
            return setIndex(new IndexAdapter<T>()
            {
                @Override
                public Collection<T> get(String token)
                {
                    return index.getAny(token);
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
         */
        public AutocompleteEngine<T> build()
        {
            return new AutocompleteEngine<T>(this);
        }
    }
}
