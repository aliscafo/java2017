package ru.spbau.erokhina.hw6.src;

/**
 * Interface that represents a predicate with obe argument and boolean returned value.
 * @param <X> - type of the argument.
 */
public interface Predicate<X> extends Function1<X, Boolean> {
    /**
     * Gets one predicate as an argument. Returns predicate that is disjuncture of current predicate and the argument.
     * @param snd - argument, second predicate.
     * @return predicate that is disjuncture of current predicate and the argument.
     */
    default Predicate<X> or (Predicate<X> snd) {
        return x -> (this.apply(x) || snd.apply(x));
    }

    /**
     * Gets one predicate as an argument. Returns predicate that is conjuncture of current predicate and the argument.
     * @param snd - argument, second predicate.
     * @return predicate that is conjuncture of current predicate and the argument.
     */
    default Predicate<X> and (Predicate<X> snd) {
        return x -> (this.apply(x) && snd.apply(x));
    }

    /**
     * Returns predicate that is "not" of current predicate.
     * @return predicate that is "not" of current predicate.
     */
    default Predicate<X> not () {
        return x -> (!this.apply(x));
    }

    /**
     * Constant predicate that always returns "true".
     * @param <X> - type of the argument.
     * @return always "true".
     */
    static <X> Predicate<X> ALWAYS_TRUE () {
        return x -> true;
    }
    /**
     * Constant predicate that always returns "false".
     * @param <X> - type of the argument.
     * @return always "false".
     */
    static <X> Predicate<X> ALWAYS_FALSE () {
        return x -> false;
    }
}
