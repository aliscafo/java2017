package ru.spbau.erokhina.maybe.src;

import java.io.*;
import java.util.Scanner;
import java.util.function.Function;

/**
 * Class that represents option type that may or may not contain a meaningful value.
 */
public class Maybe<T> {
    T value;

    private Maybe (T t) {
        value = t;
    }

    private Maybe () {
        value = null;
    }

    /**
     * Creates new instance of Maybe that stores meaningful value.
     * @param t - given value.
     * @param <T> - type of value.
     * @return new instance of Maybe with given value.
     */
    public static <T> Maybe<T> just(T t) {
        Maybe newMaybe = new Maybe(t);
        return newMaybe;
    }

    /**
     * Creates new instance of Maybe without stored value.
     * @param <T> - type of potential values.
     * @return new instance of Maybe without stored value.
     */
    public static <T> Maybe<T> nothing() {
        Maybe newMaybe = new Maybe();
        return newMaybe;
    }

    /**
     * Returns stored value if it exists. Throws exception otherwise.
     * @return stored value if it exists.
     * @throws ExceptionMaybe if current Maybe instance contains nothing.
     */
    public T get() throws ExceptionMaybe {
        if (value == null) {
            throw new ExceptionMaybe("The value doesn't exist.");
        }
        return value;
    }

    /**
     * Returns true if value exists. False otherwise.
     * @return true if value exists. False otherwise.
     */
    public boolean isPresent() {
        if (value == null) {
            return false;
        }
        return true;
    }

    /**
     * Gets function and applies it to value of current Maybe. Returns new Maybe instance with
     * returned value by the function. Returns empty Maybe if current Maybe is empty.
     * @param mapper - given function.
     * @param <U> - type of returned value.
     * @return Returns new Maybe instance with returned value by the function.
     * Returns empty Maybe if current Maybe is empty.
     */
    public <U> Maybe<U> map(Function<T, U> mapper) {
        if (!isPresent()) {
            return new Maybe<>();
        }
        return new Maybe<>(mapper.apply(value));
    }

    /**
     * Tries to read integer number from current line of scanner.
     * @param scanner - given scanner.
     * @return Maybe instance with read number.
     * @throws FileNotFoundException if there is not number in the line.
     */
    public static Maybe<Integer> readNum(Scanner scanner) throws FileNotFoundException {
        try {
            Integer num = Integer.parseInt(scanner.nextLine());
            return Maybe.just(num);
        } catch (NumberFormatException ex) {
            return Maybe.nothing();
        }
    }

    /**
     * Writes given value using given writer or "null" if value is null.
     * @param writer - given writer.
     * @param num - given number.
     * @throws IOException
     */
    public static void writeNumOrNull(Writer writer, Integer num) throws IOException {
        if (num == null) {
            writer.write("null\n");
            return;
        }
        Integer numSqr = new Integer(num * num);
        writer.write(numSqr.toString() + "\n");
    }
}
