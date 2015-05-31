package completely.text.index;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TrieTest extends AbstractIndexTest<Trie<Object>>
{
    public TrieTest()
    {
        super(new Trie<Object>());
    }

    @Test
    public void testGetAny()
    {
        index.put("abc", 0);
        index.put("abcd", 1);
        assertEquals(2, index.getAny("ab").size());
        assertEquals(0, index.getAny("abd").size());
    }
}
