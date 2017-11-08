package ru.spbau.erokhina.hw6.src;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class for testing Predicate interface.
 */
public class PredicateTest {
    /**
     * Testing of "and" method.
     */
    @Test
    public void andNormalCase() throws Exception {
        Predicate<Integer> lessThan10 = new Predicate<Integer>() {
            @Override
            public Boolean apply(Integer x) {
                return x < 10;
            }
        };

        Predicate<Integer> moreThan0 = new Predicate<Integer>() {
            @Override
            public Boolean apply(Integer x) {
                return x > 0;
            }
        };

        assertTrue(lessThan10.and(moreThan0).apply(5).equals(true));
        assertTrue(lessThan10.and(moreThan0).apply(11).equals(false));
        assertTrue(lessThan10.and(moreThan0).apply(-2).equals(false));
    }

    /**
     * Testing of "or" method.
     */
    @Test
    public void orNormalCase() throws Exception {
        Predicate<Integer> moreThan10 = new Predicate<Integer>() {
            @Override
            public Boolean apply(Integer x) {
                return x > 10;
            }
        };

        Predicate<Integer> lessThan0 = new Predicate<Integer>() {
            @Override
            public Boolean apply(Integer x) {
                return x < 0;
            }
        };

        assertTrue(moreThan10.or(lessThan0).apply(5).equals(false));
        assertTrue(moreThan10.or(lessThan0).apply(287).equals(true));
        assertTrue(moreThan10.or(lessThan0).apply(-100).equals(true));
    }

    /**
     * Testing of "not" method.
     */
    @Test
    public void notNormalCase() throws Exception {
        Predicate<String> isEmpty = new Predicate<String>() {
            @Override
            public Boolean apply(String str) {
                return str.length() == 0;
            }
        };

        assertTrue(isEmpty.not().apply("aba").equals(true));
    }

    /**
     * Testing of "ALWAYS_TRUE" method.
     */
    @Test
    public void ALWAYS_TRUENormalCase() throws Exception {
        assertTrue(Predicate.ALWAYS_TRUE().apply(5));
    }

    /**
     * Testing of "ALWAYS_FALSE" method.
     */
    @Test
    public void ALWAYS_FALSENormalCase() throws Exception {
        assertFalse(Predicate.ALWAYS_FALSE().apply(5));
    }
}