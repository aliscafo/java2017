package ru.spbau.erokhina.threadpool;

public class LightExecutionException extends Exception {
    LightExecutionException() {
        super();
    }
    LightExecutionException(Exception e) {
        super(e);
    }
}
