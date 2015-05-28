package completely.text.index;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PatriciaTrieTest extends IndexTest<PatriciaTrie<Object>>
{
    public PatriciaTrieTest()
    {
        super(new PatriciaTrie<Object>());
    }

    @Test
    public void testGetAny()
    {
        index.put("abc", 0);
        index.put("abcd", 1);
        assertEquals(2, index.getAny("abc").size());
    }
}
