package completely.text.match;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressWarnings("checkstyle:multiplestringliterals")
public class EditDistanceAutomatonTest extends AbstractAutomatonTest<EditDistanceAutomaton>
{
    @Test
    public void testCreateNullPattern()
    {
        exceptionRule.expect(NullPointerException.class);
        new EditDistanceAutomaton(null, 0D);
    }

    @Test
    public void testSingleCharacterExactMatch()
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
    public void testSingleCharacterFuzzyMatch()
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

    @Test
    public void testMultiCharacterFuzzyMatch()
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

}
