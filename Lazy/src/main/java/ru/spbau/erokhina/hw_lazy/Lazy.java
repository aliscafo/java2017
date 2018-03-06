package ru.spbau.erokhina.hw_lazy;

/**
 * Interface that provides a lazy calculation.
 * @param <T> type of return value.
 */
public interface Lazy<T> {
    /**
     * Returns result of supplier's calculation.
     * Calculates value only 1 time.
     */
    T get();
}
