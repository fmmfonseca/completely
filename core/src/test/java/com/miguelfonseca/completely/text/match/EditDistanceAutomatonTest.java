package com.miguelfonseca.completely.text.match;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("checkstyle:multiplestringliterals")
public class EditDistanceAutomatonTest extends AbstractAutomatonTest<EditDistanceAutomaton>
{
    @Test
    public void testCreateNegativeThreshold()
    {
        assertThrows(
            IllegalArgumentException.class,
            () -> new EditDistanceAutomaton("", -1D)
        );
    }

    @Test
    public void testCreateNullPattern()
    {
        assertThrows(
            NullPointerException.class,
            () -> new EditDistanceAutomaton(null, 0D)
        );
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
        automaton = new EditDistanceAutomaton("", 0D);
        assertEquals(1, automaton.getScore(), 0D);
        automaton = automaton.step('a');
        assertEquals(0, automaton.getScore(), 0D);
    }

    @Test
    public void testGetScore()
    {
        automaton = new EditDistanceAutomaton("abcd", 0D);
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
    public void testExactMatchSingleCharacterStep()
    {
        automaton = new EditDistanceAutomaton("a", 0D);
        assertFalse(automaton.isWordAccepted());
        assertFalse(automaton.isWordRejected());
        automaton = automaton.step('a');
        assertTrue(automaton.isWordAccepted());
        assertFalse(automaton.isWordRejected());
        automaton = automaton.step('b');
        assertFalse(automaton.isWordAccepted());
        assertTrue(automaton.isWordRejected());
    }

    @Test
    public void testFuzzyMatchMultiCharacterStep()
    {
        automaton = new EditDistanceAutomaton("abcd", 1D);
        assertFalse(automaton.isWordAccepted());
        assertFalse(automaton.isWordRejected());
        automaton = automaton.step("bc");
        assertFalse(automaton.isWordAccepted());
        assertFalse(automaton.isWordRejected());
        automaton = automaton.stepUntilWordAccepted("de");
        assertTrue(automaton.isWordAccepted());
        assertFalse(automaton.isWordRejected());
    }

    @Test
    public void testFuzzyMatchSingleCharacterStep()
    {
        automaton = new EditDistanceAutomaton("a", 1D);
        assertTrue(automaton.isWordAccepted());
        assertFalse(automaton.isWordRejected());
        automaton = automaton.step('a');
        assertTrue(automaton.isWordAccepted());
        assertFalse(automaton.isWordRejected());
        automaton = automaton.step('b');
        assertTrue(automaton.isWordAccepted());
        assertFalse(automaton.isWordRejected());
        automaton = automaton.step('c');
        assertFalse(automaton.isWordAccepted());
        assertTrue(automaton.isWordRejected());
    }
}
