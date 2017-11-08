package ru.spbau.erokhina.hw6.src;

/**
 * Interface that represents a function with one argument and one returned value.
 * @param <X> - type of argument.
 * @param <Y> - type of returned value.
 */
public interface Function1<X, Y> {
    /**
     * Composes two functions to one: g(f(x)).
     * @param g - outside function.
     * @param <U> - type of returned value of the outside function.
     * @return function with one argument and one returned value.
     */
    default <U> Function1<X, U> compose(Function1<Y, U> g) {
        return x -> g.apply(this.apply(x));
    }

    /**
     * Application of the function.
     * @param x - type of the argument.
     * @return returned value after application.
     */
    Y apply (X x);
}
