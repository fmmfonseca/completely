package com.miguelfonseca.completely.common;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PreconditionTest
{
    @Test
    public void testCheckArgument()
    {
        Precondition.checkArgument(true);
    }

    @Test
    public void testCheckArgumentMessage()
    {
        Precondition.checkArgument(true, "");
    }

    @Test
    public void testCheckArgumentFail()
    {
        assertThrows(
            IllegalArgumentException.class,
            () -> Precondition.checkArgument(false)
        );
    }

    @Test
    public void testCheckArgumentFailMessage()
    {
        assertThrows(
            IllegalArgumentException.class,
            () -> Precondition.checkArgument(false, ""),
            ""
        );
    }

    @Test
    public void testCheckArithmetic()
    {
        Precondition.checkArithmetic(true);
    }

    @Test
    public void testCheckArithmeticMessage()
    {
        Precondition.checkArithmetic(true, "");
    }

    @Test
    public void testCheckArithmeticFail()
    {
        assertThrows(
            ArithmeticException.class,
            () -> Precondition.checkArithmetic(false)
        );
    }

    @Test
    public void testCheckArithmeticFailMessage()
    {
        assertThrows(
            ArithmeticException.class,
            () -> Precondition.checkArithmetic(false, ""),
            ""
        );
    }

    @Test
    public void testCheckElement()
    {
        Precondition.checkElement(true);
    }

    @Test
    public void testCheckElementMessage()
    {
        Precondition.checkElement(true, "");
    }

    @Test
    public void testCheckElementFail()
    {
        assertThrows(
            NoSuchElementException.class,
            () ->  Precondition.checkElement(false)
        );
    }

    @Test
    public void testCheckElementFailMessage()
    {
        assertThrows(
            NoSuchElementException.class,
            () -> Precondition.checkElement(false, ""),
            ""
        );
    }

    @Test
    public void testCheckIndex()
    {
        Precondition.checkIndex(true);
    }

    @Test
    public void testCheckIndexMessage()
    {
        Precondition.checkIndex(true, "");
    }

    @Test
    public void testCheckIndexFail()
    {
        assertThrows(
            IndexOutOfBoundsException.class,
            () -> Precondition.checkIndex(false)
        );
    }

    @Test
    public void testCheckIndexFailMessage()
    {
        assertThrows(
            IndexOutOfBoundsException.class,
            () -> Precondition.checkIndex(false, ""),
            ""
        );
    }

    @Test
    public void testCheckPointer()
    {
        Precondition.checkPointer(true);
    }

    @Test
    public void testCheckPointerMessage()
    {
        Precondition.checkPointer(true, "");
    }

    @Test
    public void testCheckPointerFail()
    {
        assertThrows(
            NullPointerException.class,
            () -> Precondition.checkPointer(false)
        );
    }

    @Test
    public void testCheckPointerFailMessage()
    {
        assertThrows(
            NullPointerException.class,
            () -> Precondition.checkPointer(false, ""),
            ""
        );
    }

    @Test
    public void testCheckState()
    {
        Precondition.checkState(true);
    }

    @Test
    public void testCheckStateMessage()
    {
        Precondition.checkState(true, "");
    }

    @Test
    public void testCheckStateFail()
    {
        assertThrows(
            IllegalStateException.class,
            () -> Precondition.checkState(false)
        );
    }

    @Test
    public void testCheckStateFailMessage()
    {
        assertThrows(
            IllegalStateException.class,
            () -> Precondition.checkState(false, ""),
            ""
        );
    }
}
