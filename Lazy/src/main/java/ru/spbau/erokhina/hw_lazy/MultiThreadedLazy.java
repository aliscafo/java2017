package ru.spbau.erokhina.hw_lazy;

import java.util.function.Supplier;

/**
 * Class that provides thread-safe Lazy.
 * @param <T> type of return value
 */
@SuppressWarnings("WeakerAccess")
public class MultiThreadedLazy<T> implements Lazy<T> {
    private volatile Supplier<T> supplier;
    private volatile T result;

    /**
     * Constructor for MultiThreadedLazy.
     * @param supplier given supplier with calculation
     */
    public MultiThreadedLazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    /**
     * Returns result of supplier's calculation.
     * Calculates value only 1 time.
     */
    @Override
    public T get() {
        if (supplier == null) {
            return result;
        }

        synchronized (this) {
            if (supplier != null) {
                result = supplier.get();
                supplier = null;
            }
        }

        return result;
    }
}
