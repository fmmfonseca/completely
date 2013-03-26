package completely.text.index;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HashTrieTest extends IndexTest<HashTrie<Object>>
{
    public HashTrieTest()
    {
        super(new HashTrie<Object>());
    }

    @Test
    public void testGetAny()
    {
        index.put("abc", 0);
        index.put("abcd", 1);
        assertEquals(2, index.getAny("abc").size());
    }
}
