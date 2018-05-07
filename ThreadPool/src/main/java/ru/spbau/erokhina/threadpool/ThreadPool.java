package ru.spbau.erokhina.threadpool;

import java.util.function.Supplier;

/**
 * Interface for ThreadPool (for achieving concurrency of execution of tasks).
 */
public interface ThreadPool {
    /**
     * All threads will be stopped.
     */
    void shutdown();

    /**
     * Adds a task to the pool.
     * @param supplier supplier that holds a task
     * @param <T> a type of return value
     * @return a new task that was added to the pool
     */
    <T> LightFuture<T> add(Supplier<T> supplier);
}
