package completely.text.match;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EqualityAutomatonTest extends AutomatonTest<EqualityAutomaton>
{
    @Test
    public void testCreateNullPattern()
    {
        exceptionRule.expect(NullPointerException.class);
        new EqualityAutomaton(null);
    }

    @Test
    public void testSingleCharacterMatch()
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
