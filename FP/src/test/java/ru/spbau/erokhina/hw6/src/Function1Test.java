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
        Function1<Integer, Integer> sqr = new Function1<Integer, Integer>() {
            @Override
            public Integer apply(Integer x) {
                return x*x;
            }
        };

        Function1<Integer, Integer> plus3 = new Function1<Integer, Integer>() {
            @Override
            public Integer apply(Integer x) {
                return x + 3;
            }
        };

        assertTrue(plus3.compose(sqr).apply(4).equals(49));
    }

    /**
     * Testing of compose method. Just normal case with Strings.
     */
    @Test
    public void composeNormalCaseString() throws Exception {
        Function1<String, String> doubleS = new Function1<String, String>() {
            @Override
            public String apply(String x) {
                return x + x;
            }
        };

        Function1<String, String> addDot = new Function1<String, String>() {
            @Override
            public String apply(String x) {
                return x + ".";
            }
        };

        assertTrue(doubleS.compose(addDot).apply("ha").equals("haha."));
    }

    /**
     * Testing of apply method. Just normal case with Integers.
     */
    @Test
    public void applyNormalCase() throws Exception {
        Function1<Integer, Integer> prod5 = new Function1<Integer, Integer>() {
            @Override
            public Integer apply(Integer x) {
                return x*5;
            }
        };

        assertTrue(prod5.apply(4).equals(20));
    }
}