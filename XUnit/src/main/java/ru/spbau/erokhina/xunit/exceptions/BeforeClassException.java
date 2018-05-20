package ru.spbau.erokhina.xunit.exceptions;

/**
 * Exception that is thrown if something went wrong in BeforeClass method.
 */
public class BeforeClassException extends Exception {
    public BeforeClassException(Exception e) {
        super(e);
    }
}
