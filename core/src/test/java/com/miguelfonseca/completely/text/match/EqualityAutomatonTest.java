package com.miguelfonseca.completely.text.match;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("checkstyle:multiplestringliterals")
public class EqualityAutomatonTest extends AbstractAutomatonTest<EqualityAutomaton>
{
    @Test
    public void testCreateNullPattern()
    {
        exceptionRule.expect(NullPointerException.class);
        new EqualityAutomaton(null);
    }

    @Test
    public void testCreateEmptyPattern()
    {
        automaton = new EqualityAutomaton("");
        assertTrue(automaton.isWordAccepted());
        assertFalse(automaton.isWordRejected());
    }

    @Test
    public void testGetScoreEmptyPattern()
    {
        automaton = new EqualityAutomaton("");
        assertEquals(1, automaton.getScore(), 0D);
        automaton = automaton.step('a');
        assertEquals(0, automaton.getScore(), 0D);
    }

    @Test
    public void testGetScore()
    {
        automaton = new EqualityAutomaton("abcd");
        assertEquals(0, automaton.getScore(), 0);
        automaton = automaton.step('a');
        assertEquals(0.25, automaton.getScore(), 0D);
        automaton = automaton.step('b');
        assertEquals(0.5, automaton.getScore(), 0D);
        automaton = automaton.step('c');
        assertEquals(0.75, automaton.getScore(), 0D);
        automaton = automaton.step('d');
        assertEquals(1, automaton.getScore(), 0D);
        automaton = automaton.step('e');
        assertEquals(0.8, automaton.getScore(), 0D);
    }

    @Test
    public void testMatchMultiCharacterStep()
    {
        automaton = new EqualityAutomaton("abcd");
        assertFalse(automaton.isWordAccepted());
        assertFalse(automaton.isWordRejected());
        automaton = automaton.step("ab");
        assertFalse(automaton.isWordAccepted());
        assertFalse(automaton.isWordRejected());
        automaton = automaton.stepUntilWordAccepted("cde");
        assertTrue(automaton.isWordAccepted());
        assertFalse(automaton.isWordRejected());
    }

    @Test
    public void testMatchSingleCharacterStep()
    {
        automaton = new EqualityAutomaton("a");
        assertFalse(automaton.isWordAccepted());
        assertFalse(automaton.isWordRejected());
        automaton = automaton.step('a');
        assertTrue(automaton.isWordAccepted());
        assertFalse(automaton.isWordRejected());
        automaton = automaton.step('b');
        assertFalse(automaton.isWordAccepted());
        assertTrue(automaton.isWordRejected());
    }
}
