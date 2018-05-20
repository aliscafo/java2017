package ru.spbau.erokhina.xunit.exceptions;

/**
 * Exception that is thrown if something went wrong in AfterClass method.
 */
public class AfterClassException extends Exception {
    public AfterClassException(Exception e) {
        super(e);
    }
}
