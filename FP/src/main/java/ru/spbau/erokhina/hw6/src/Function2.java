package ru.spbau.erokhina.hw6.src;

/**
 * Interface that represents a function with two arguments and one returned value.
 * @param <X> - type of the first argument.
 * @param <Y> - type of the second argument.
 * @param <Z> - type of the returned value.
 */
public interface Function2<X, Y, Z> {
    /**
     * Composes two functions to one: g(f(x, y)).
     * @param g - outside function.
     * @param <U> - type of returned value of the outside function.
     * @return function with with arguments and one returned value.
     */
    default <U> Function2<X, Y, U> compose(Function1<? super Z, U> g) {
        return (x, y) -> g.apply(this.apply(x, y));
    }

    /**
     * Gets first value and returns a function with only one argument instead of two.
     * @param x - type of first argument.
     * @return a function with only one argument instead of two.
     */
    default Function1<Y, Z> bind1(X x) {
        return y -> this.apply(x, y);
    }

    /**
     * Gets second value and returns a function with only one argument instead of two.
     * @param y - type of second argument.
     * @return a function with only one argument instead of two.
     */
    default Function1<X, Z> bind2(Y y) {
        return x -> this.apply(x, y);
    }

    /**
     * Currying. Returns function with one argument.
     * @return function with one argument.
     */
    default Function1<X, Function1<Y, Z>> curry() {
        return x -> (y -> this.apply(x, y));
    }

    /**
     * Application of the function.
     * @param x - type of the first argument.
     * @param y - type of the second argument.
     * @return returned value after application.
     */
    Z apply(X x, Y y);
}
