package completely.text.match;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EditDistanceAutomatonTest
{
    private Automaton automaton;

    @Test
    public void testExactMatch()
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
    public void testFuzzyMatch()
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
