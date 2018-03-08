package ru.spbau.erokhina.threadpool;

import java.util.function.Supplier;

/**
 * Interface for ThreadPool (for achieving concurrency of execution of tasks).
 */
public interface ThreadPool {
    void shutdown();
    <T> LightFuture<T> add(Supplier<T> supplier);
}
