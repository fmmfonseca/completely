package com.miguelfonseca.completely.common;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("checkstyle:multiplestringliterals")
public class StringsTest
{
    @Test
    public void testGetCommonPrefixLength()
    {
        assertEquals(3, Strings.getCommonPrefixLength("abc", "abc"));
        assertEquals(2, Strings.getCommonPrefixLength("abc", "abd"));
        assertEquals(2, Strings.getCommonPrefixLength("abd", "abc"));
        assertEquals(0, Strings.getCommonPrefixLength("abc", ""));
        assertEquals(0, Strings.getCommonPrefixLength("", "abc"));
        assertEquals(0, Strings.getCommonPrefixLength(null, "abc"));
        assertEquals(0, Strings.getCommonPrefixLength("abc", null));
    }
}
