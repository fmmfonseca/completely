package completely.data;

import java.util.List;

/**
 * Unit of indexing and search.
 */
public interface Indexable
{
    List<String> getFields();

    double getScore();
}
