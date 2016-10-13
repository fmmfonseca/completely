package com.miguelfonseca.completely.common;

import java.util.NoSuchElementException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PreconditionTest
{
    @Rule
    @SuppressWarnings("checkstyle:visibilitymodifier")
    public ExpectedException exceptionRule;

    public PreconditionTest()
    {
        this.exceptionRule = ExpectedException.none();
    }

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
        exceptionRule.expect(IllegalArgumentException.class);
        Precondition.checkArgument(false);
    }

    @Test
    public void testCheckArgumentFailMessage()
    {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("");
        Precondition.checkArgument(false, "");
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
        exceptionRule.expect(ArithmeticException.class);
        Precondition.checkArithmetic(false);
    }

    @Test
    public void testCheckArithmeticFailMessage()
    {
        exceptionRule.expect(ArithmeticException.class);
        exceptionRule.expectMessage("");
        Precondition.checkArithmetic(false, "");
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
        exceptionRule.expect(NoSuchElementException.class);
        Precondition.checkElement(false);
    }

    @Test
    public void testCheckElementFailMessage()
    {
        exceptionRule.expect(NoSuchElementException.class);
        exceptionRule.expectMessage("");
        Precondition.checkElement(false, "");
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
        exceptionRule.expect(IndexOutOfBoundsException.class);
        Precondition.checkIndex(false);
    }

    @Test
    public void testCheckIndexFailMessage()
    {
        exceptionRule.expect(IndexOutOfBoundsException.class);
        exceptionRule.expectMessage("");
        Precondition.checkIndex(false, "");
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
        exceptionRule.expect(NullPointerException.class);
        Precondition.checkPointer(false);
    }

    @Test
    public void testCheckPointerFailMessage()
    {
        exceptionRule.expect(NullPointerException.class);
        exceptionRule.expectMessage("");
        Precondition.checkPointer(false, "");
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
        exceptionRule.expect(IllegalStateException.class);
        Precondition.checkState(false);
    }

    @Test
    public void testCheckStateFailMessage()
    {
        exceptionRule.expect(IllegalStateException.class);
        exceptionRule.expectMessage("");
        Precondition.checkState(false, "");
    }
}
