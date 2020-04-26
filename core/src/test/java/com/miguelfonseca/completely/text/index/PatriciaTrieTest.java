package com.miguelfonseca.completely.text.index;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PatriciaTrieTest extends AbstractIndexTest<PatriciaTrie<Object>>
{
    public PatriciaTrieTest()
    {
        super(new PatriciaTrie<>());
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
