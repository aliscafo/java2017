package ru.spbau.erokhina.threadpool;

public class LightExecutionException extends Exception {
    LightExecutionException(Exception e) {
        super(e);
    }
}
