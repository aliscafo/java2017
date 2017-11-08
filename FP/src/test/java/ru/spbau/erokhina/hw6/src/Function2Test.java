package ru.spbau.erokhina.hw6.src;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class for testing Function2 interface.
 */
public class Function2Test {
    /**
     * Testing of compose method. Just normal case with Integers.
     */
    @Test
    public void composeNormalCase() throws Exception {
        Function2<Integer, Integer, Integer> sum = new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer x, Integer y) {
                return x + y;
            }
        };

        Function1<Integer, Integer> sqr = new Function1<Integer, Integer>() {
            @Override
            public Integer apply(Integer x) {
                return x*x;
            }
        };

        assertTrue(sum.compose(sqr).apply(5, 3).equals(64));
    }

    /**
     * Testing of bind1 method. Just normal case with Integers.
     */
    @Test
    public void bind1NormalCase() throws Exception {
        Function2<Integer, Integer, Integer> sum = new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer x, Integer y) {
                return x + y;
            }
        };

        Function1 func1 = sum.bind1(5);

        assertTrue(func1.apply(6).equals(11));
    }

    /**
     * Testing of bind2 method. Just normal case with Strings.
     */
    @Test
    public void bind1String() throws Exception {
        Function2<String, String, String> concat = new Function2<String, String, String>() {
            @Override
            public String apply(String x, String y) {
                return x + y;
            }
        };

        Function1 func1 = concat.bind1("be ");

        assertTrue(func1.apply("happy").equals("be happy"));
    }

    /**
     * Testing of bind2 method. Just normal case with Integers.
     */
    @Test
    public void bind2NormalCase() throws Exception {
        Function2<Integer, Integer, Integer> sum = new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer x, Integer y) {
                return x + y;
            }
        };

        Function1 func1 = sum.bind2(5);

        assertTrue(func1.apply(6).equals(11));
    }

    /**
     * Testing of bind2 method. Just normal case with Strings.
     */
    @Test
    public void bind2String() throws Exception {
        Function2<String, String, String> concat = new Function2<String, String, String>() {
            @Override
            public String apply(String x, String y) {
                return x + y;
            }
        };

        Function1 func1 = concat.bind2("!");

        assertTrue(func1.apply("okay").equals("okay!"));
    }

    /**
     * Testing of curry method. Just normal case with Integers.
     */
    @Test
    public void curry() throws Exception {
        Function2<Integer, Integer, Integer> sum = new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer x, Integer y) {
                return x + y;
            }
        };

        assertTrue(sum.curry().apply(5).apply(6).equals(11));
    }

    /**
     * Testing of apply method. Just normal case with Integers.
     */
    @Test
    public void apply() throws Exception {
        Function2<Integer, Integer, Integer> sum = new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer x, Integer y) {
                return 2 * x + 3 * y;
            }
        };

        assertTrue(sum.apply(10,100).equals(320));
    }
}