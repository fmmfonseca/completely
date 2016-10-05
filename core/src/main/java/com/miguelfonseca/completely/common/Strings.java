package com.miguelfonseca.completely.common;

import javax.annotation.Nullable;

/**
 * Operations on {@link String}.
 */
public final class Strings
{
    @SuppressWarnings("checkstyle:leftcurly")
    private Strings() { };

    /**
     * Returns the longest common prefix length.
     */
    public static int getCommonPrefixLength(@Nullable String a, @Nullable String b)
    {
        if (a == null || b == null)
        {
            return 0;
        }
        int length = Math.min(a.length(), b.length());
        for (int i = 0; i < length; ++i)
        {
            if (a.charAt(i) != b.charAt(i))
            {
                return i;
            }
        }
        return length;
    }
}
