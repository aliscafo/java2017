package ru.spbau.erokhina.hw6.src;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Class that contains some useful methods for working with Collections.
 */
public class Collections {
    /**
     * Gets a function with one argument and a collection. Applies function to each element of the collection and returns
     * a list with modified elements.
     * @param func - function with one argument.
     * @param collection - a collection of elements.
     * @param <X> - type of argument of the function.
     * @param <Y> - type of the function's returned value.
     * @return a list with modified elements.
     */
    static <X, Y> ArrayList<? super Y> map(Function1<X, Y> func, Collection<X> collection) {
        List<Y> res = new ArrayList<Y>();
        for (X elem : collection) {
            res.add(func.apply(elem));
        }

        return (ArrayList<? super Y>) res;
    }

    /**
     * Gets a predicate and a collection. Returns a list of the elements from the collections for what the predicate
     * is true.
     * @param predicate - a predicate with one argument.
     * @param collection - a collection of elemets.
     * @param <X> - type of elements in the collection.
     * @return a list of the elements from the collections for what the predicate is true.
     */
    static <X> ArrayList<X> filter(Predicate<X> predicate, Collection<X> collection) {
        List<X> res = new ArrayList<X>();

        for (X elem : collection) {
            if (predicate.apply(elem))
                res.add(elem);
        }

        return (ArrayList<X>) res;
    }

    /**
     * Gets a predicate and a collection. Returns a list of the elements from the collections while the predicate for
     * them is true.
     * @param predicate - a predicate with one argument.
     * @param collection - a collection of elemets.
     * @param <X> - type of elements in the collection.
     * @return a list of the elements from the collections while the predicate for them is true.
     */
    static <X> ArrayList<X> takeWhile(Predicate<X> predicate, Collection<X> collection) {
        List<X> res = new ArrayList<X>();

        for (X elem : collection) {
            if (!predicate.apply(elem))
                break;
            res.add(elem);
        }

        return (ArrayList<X>) res;
    }

    /**
     * Gets a predicate and a collection. Returns a list of the elements from the collections while the predicate for
     * them is false.
     * @param predicate - a predicate with one argument.
     * @param collection - a collection of elemets.
     * @param <X> - type of elements in the collection.
     * @return a list of the elements from the collections while the predicate for them is false.
     */
    static <X, Y> ArrayList<X> takeUnless(Predicate<X> predicate, Collection<X> collection) {
        return takeWhile(predicate.not(), collection);
    }

    /**
     * Gets a function with two arguments, a start value and a collection. Works as foldl in Haskell.
     * @param func - function with two arguments.
     * @param start - start value.
     * @param collection - collection of elements.
     * @param <X> - type of the start element and returned value.
     * @param <Y> - type of the elements in the collection.
     * @return a value after applications for all elements.
     */
    static <X, Y> X foldl (Function2<X, Y, X> func, X start, Collection<Y> collection) {
        for (Y elem : collection) {
            start = func.apply(start, elem);
        }

        return start;
    }

    /**
     * Gets a function with two arguments, a start value and a collection. Works as foldr in Haskell.
     * @param func - function with two arguments.
     * @param start - start value.
     * @param collection - collection of elements.
     * @param <X> - type of the elements in the collection.
     * @param <Y> - type of the start element and returned value.
     * @return a value after applications for all elements.
     */
    static <X, Y> Y foldr(Function2<X, Y, Y> func, Y start, Collection<X> collection) {
        List<X> array = new ArrayList<X>();
        array.addAll(collection);

        for (int i = array.size() - 1; i >= 0; i--) {
            start = func.apply(array.get(i), start);
        }

        return start;
    }
}