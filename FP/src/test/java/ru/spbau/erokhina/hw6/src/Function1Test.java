package ru.spbau.erokhina.hw6.src;

import org.junit.Test;

import javax.security.sasl.SaslServer;

import static org.junit.Assert.*;

/**
 * Class for testing Function1 interface.
 */
public class Function1Test {
    /**
     * Testing of compose method. Just normal case with Integers.
     */
    @Test
    public void composeNormalCase() throws Exception {
        Function1<Integer, Integer> sqr = x -> x * x;

        Function1<Integer, Integer> plus3 = x -> x + 3;

        assertTrue(plus3.compose(sqr).apply(4).equals(49));
    }

    /**
     * Testing of compose method. Just normal case with Strings.
     */
    @Test
    public void composeNormalCaseString() throws Exception {
        Function1<String, String> doubleS = x -> x + x;

        Function1<String, String> addDot = x -> x + ".";

        assertTrue(doubleS.compose(addDot).apply("ha").equals("haha."));
    }

    /**
     * Testing of apply method. Just normal case with Integers.
     */
    @Test
    public void applyNormalCase() throws Exception {
        Function1<Integer, Integer> prod5 = x -> x * 5;

        assertTrue(prod5.apply(4).equals(20));
    }
}