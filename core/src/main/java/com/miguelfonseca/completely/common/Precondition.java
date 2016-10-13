package com.miguelfonseca.completely.common;

import java.util.NoSuchElementException;

import javax.annotation.Nullable;

/**
 * Condition that must be true prior to a routine's execution.
 */
public final class Precondition
{
    @SuppressWarnings("checkstyle:leftcurly")
    private Precondition() { };

    /**
     * Ensures the truth of an expression.
     *
     * @throws IllegalArgumentException if {@code expression} is false;
     */
    public static void checkArgument(boolean expression)
    {
        if (!expression)
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Ensures the truth of an expression.
     *
     * @throws IllegalArgumentException if {@code expression} is false;
     */
    public static void checkArgument(boolean expression, @Nullable String message)
    {
        if (!expression)
        {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Ensures the truth of an expression.
     *
     * @throws ArithmeticException if {@code expression} is false;
     */
    public static void checkArithmetic(boolean expression)
    {
        if (!expression)
        {
            throw new ArithmeticException();
        }
    }

    /**
     * Ensures the truth of an expression.
     *
     * @throws ArithmeticException if {@code expression} is false;
     */
    public static void checkArithmetic(boolean expression, @Nullable String message)
    {
        if (!expression)
        {
            throw new ArithmeticException(message);
        }
    }

    /**
     * Ensures the truth of an expression.
     *
     * @throws NoSuchElementException if {@code expression} is false;
     */
    public static void checkElement(boolean expression)
    {
        if (!expression)
        {
            throw new NoSuchElementException();
        }
    }

    /**
     * Ensures the truth of an expression.
     *
     * @throws NoSuchElementException if {@code expression} is false;
     */
    public static void checkElement(boolean expression, @Nullable String message)
    {
        if (!expression)
        {
            throw new NoSuchElementException(message);
        }
    }

    /**
     * Ensures the truth of an expression.
     *
     * @throws IndexOutOfBoundsException if {@code expression} is false;
     */
    public static void checkIndex(boolean expression)
    {
        if (!expression)
        {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Ensures the truth of an expression.
     *
     * @throws IndexOutOfBoundsException if {@code expression} is false;
     */
    public static void checkIndex(boolean expression, @Nullable String message)
    {
        if (!expression)
        {
            throw new IndexOutOfBoundsException(message);
        }
    }

    /**
     * Ensures the truth of an expression.
     *
     * @throws NullPointerException if {@code expression} is false;
     */
    public static void checkPointer(boolean expression)
    {
        if (!expression)
        {
            throw new NullPointerException();
        }
    }

    /**
     * Ensures the truth of an expression.
     *
     * @throws NullPointerException if {@code expression} is false;
     */
    public static void checkPointer(boolean expression, @Nullable String message)
    {
        if (!expression)
        {
            throw new NullPointerException(message);
        }
    }

    /**
     * Ensures the truth of an expression.
     *
     * @throws IllegalStateException if {@code expression} is false;
     */
    public static void checkState(boolean expression)
    {
        if (!expression)
        {
            throw new IllegalStateException();
        }
    }

    /**
     * Ensures the truth of an expression.
     *
     * @throws IllegalStateException if {@code expression} is false;
     */
    public static void checkState(boolean expression, @Nullable String message)
    {
        if (!expression)
        {
            throw new IllegalStateException(message);
        }
    }
}
