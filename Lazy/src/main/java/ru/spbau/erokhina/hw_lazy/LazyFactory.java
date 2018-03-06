package ru.spbau.erokhina.hw_lazy;

import java.util.function.Supplier;

/**
 * Provides a Lazy instance of two types: simple (single threaded) Lazy and thread-safe Lazy.
 */
@SuppressWarnings("WeakerAccess")
public class LazyFactory {
    /**
     * Provides a simple (single threaded) Lazy.
     * @param supplier given supplier with calculation
     * @param <T> type of return value
     * @return a simple (single threaded) Lazy
     */
    public static <T> Lazy<T> createSimpleLazy(Supplier<T> supplier) {
        return new SimpleLazy<>(supplier);
    }

    /**
     * Provides a thread-safe Lazy.
     * @param supplier given supplier with calculation
     * @param <T> type of return value
     * @return a thread-safe Lazy
     */
    public static <T> Lazy<T> createMultiThreadedLazy(Supplier<T> supplier) {
        return new MultiThreadedLazy<>(supplier);
    }
}
