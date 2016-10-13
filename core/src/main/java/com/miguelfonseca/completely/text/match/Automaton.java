package com.miguelfonseca.completely.text.match;

/**
 * Stateful text matching engine.
 */
public interface Automaton
{
    /**
     * Returns the pattern.
     */
    String getPattern();

    /**
     * Returns the word score.
     */
    double getScore();

    /**
     * Returns the word.
     */
    String getWord();

    /**
     * Returns {@code true} if the word is accepted.
     */
    boolean isWordAccepted();

    /**
     * Returns {@code true} if the word is rejected.
     */
    boolean isWordRejected();

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

    /**
     * Returns a new automaton transitioned to another state.
     *
     * @throws NullPointerException if {@code symbols} is null;
     */
    Automaton stepUntilWordAccepted(String symbols);
}
