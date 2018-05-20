package ru.spbau.erokhina.xunit.exceptions;

/**
 * Exception that is thrown when something went wrong in test class or method.
 */
public class TestException extends Exception{
    public TestException(Exception e) {
        super(e);
    }
}
