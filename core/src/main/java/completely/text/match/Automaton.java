package completely.text.match;

/**
 * Stateful text matching engine.
 */
public interface Automaton
{
    /**
     * Returns <tt>true</tt> if the word is accepted.
     */
    boolean isWordAccepted();

    /**
     * Returns <tt>true</tt> if the word is rejected.
     */
    boolean isWordRejected();

    /**
     * Returns the pattern.
     */
    String getPattern();

    /**
     * Returns a new <tt>automaton</tt> transitioned to another state.
     */
    Automaton step(char symbol);

    /**
     * Returns a new <tt>automaton</tt> transitioned to another state.
     */
    Automaton step(String symbols);
}
