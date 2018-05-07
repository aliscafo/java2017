package ru.spbau.erokhina.threadpool;

import java.util.function.Function;

/**
 * Interface for a task.
 * @param <T>
 */
public interface LightFuture<T> {
    /**
     * Returns true if the task was completed.
     * @return true if the task was completed
     */
    boolean isReady();

    /**
     * Returns a result of calculation.
     * @return a result of calculation.
     */
    T get() throws LightExecutionException;

    /**
     * Apply a given function to the result of a task.
     * @param function given function
     * @param <U> return value
     * @return a new task
     */
    <U> LightFuture<U> thenApply(Function<? super T, ? extends U> function);
}
