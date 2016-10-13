package com.miguelfonseca.completely.text.match;

import org.junit.Rule;
import org.junit.rules.ExpectedException;

public abstract class AbstractAutomatonTest<T extends Automaton>
{
    @Rule
    @SuppressWarnings("checkstyle:visibilitymodifier")
    public ExpectedException exceptionRule;

    protected Automaton automaton;

    public AbstractAutomatonTest()
    {
        this.exceptionRule = ExpectedException.none();
    }
}
