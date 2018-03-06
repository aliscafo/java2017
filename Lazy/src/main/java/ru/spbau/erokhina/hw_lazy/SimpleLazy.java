package ru.spbau.erokhina.hw_lazy;

import java.util.function.Supplier;

/**
 * Class that provides simple (single threaded) Lazy.
 * @param <T> type of return value
 */
@SuppressWarnings("WeakerAccess")
public class SimpleLazy<T> implements Lazy<T> {
    private Supplier<T> supplier;
    private T result;

    /**
     * Constructor for SimpleLazy.
     * @param supplier given supplier with calculation
     */
    public SimpleLazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    /**
     * Returns result of supplier's calculation.
     * Calculates value only 1 time.
     */
    @Override
    public T get() {
        if (supplier != null) {
            result = supplier.get();
            supplier = null;
        }
        return result;
    }
}
