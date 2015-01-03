package completely.text.match;

/**
 * Stateful text matching engine.
 */
public interface Automaton
{
    /**
     * Returns {@code true} if the word is accepted.
     */
    boolean isWordAccepted();

    /**
     * Returns {@code true} if the word is rejected.
     */
    boolean isWordRejected();

    /**
     * Returns the pattern.
     */
    String getPattern();

    /**
     * Returns a new automaton transitioned to another state.
     */
    Automaton step(char symbol);

    /**
     * Returns a new automaton transitioned to another state.
     *
     * @throws NullPointerException if {@code symbols} is null;
     */
    Automaton step(String symbols);
}
