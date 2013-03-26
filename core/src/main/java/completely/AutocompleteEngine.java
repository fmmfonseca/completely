package completely;

import completely.data.Record;
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
 * Facade for indexing and searching {@link Record} instances.
 */
public class AutocompleteEngine<T extends Record>
{
    private final IndexAdapter<T> index;
    private final Analyzer analyzer;
    private final Comparator<T> comparator;
    private final Lock read;
    private final Lock write;

    public AutocompleteEngine(final Index<T> index, Analyzer analyzer)
    {
        this(new IndexAdapter<T>()
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
        }
        , analyzer);
    }

    public AutocompleteEngine(final FuzzyIndex<T> index, Analyzer analyzer)
    {
        this(new IndexAdapter<T>()
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
        }
        , analyzer);
    }

    public AutocompleteEngine(IndexAdapter<T> index, Analyzer analyzer)
    {
        this.index = index;
        this.analyzer = analyzer;
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
        ReadWriteLock lock = new ReentrantReadWriteLock();
        this.read = lock.readLock();
        this.write = lock.writeLock();
    }

    /**
     * Indexes a single record.
     */
    public boolean add(T record)
    {
        write.lock();
        try
        {
            for (String token : analyzer.apply(record.getText()))
            {
                index.put(token, record);
            }
            return true;
        }
        finally
        {
            write.unlock();
        }
    }

    /**
     * Indexes multiple records.
     */
    public boolean add(Iterable<T> records)
    {
        for (T record : records)
        {
            add(record);
        }
        return true;
    }

    /**
     * Returns a {@link List} of all records that match a query, sorted
     * according to its score.
     */
    public List<T> search(String query)
    {
        return search(query, comparator);
    }

    /**
     * Returns a {@link List} of all records that match a query, sorted
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
     * Returns a {@link List} of the top records that match a query.
     */
    public List<T> search(String query, int limit)
    {
        return search(query, comparator, limit);
    }

    /**
     * Returns a {@link List} of the top records that match a query, sorted
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
}
