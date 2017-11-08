package ru.spbau.erokhina.hw6.src;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Class for testing Collections methods.
 */
public class CollectionsTest {
    /**
     * Testing of map method.
     */
    @Test
    public void mapNormalCase() throws Exception {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            list.add(i);

        List<Integer> expectedList = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            expectedList.add(i*i);

        Function1<Integer, Integer> sqr = new Function1<Integer, Integer>() {
            @Override
            public Integer apply(Integer x) {
                return x*x;
            }
        };

        ArrayList<Integer> res = (ArrayList<Integer>) Collections.map(sqr, list);

        assertEquals(expectedList.size(), res.size());

        for (int i = 0; i < 5; i++) {
            assertTrue(res.get(i).equals(expectedList.get(i)));
        }
    }

    /**
     * Testing of filter method.
     */
    @Test
    public void filterNormalCase() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("AAAZZZ");
        list.add("BBBTTT");
        list.add("ZZZBBB");
        list.add("PPPQQQ");
        list.add("VVVZZZ");

        List<String> expectedList = new ArrayList<>();
        expectedList.add("AAAZZZ");
        expectedList.add("ZZZBBB");
        expectedList.add("VVVZZZ");

        Predicate<String> containsZ = new Predicate<String>() {
            @Override
            public Boolean apply(String str) {
                return str.contains("Z");
            }
        };

        ArrayList<String> res = (ArrayList<String>) Collections.filter(containsZ, list);

        assertEquals(expectedList.size(), res.size());

        for (int i = 0; i < res.size(); i++)
            assertTrue(res.get(i).equals(expectedList.get(i)));
    }

    /**
     * Testing of takeWhile method.
     */
    @Test
    public void takeWhileNormalCase() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("abc");
        list.add("oooooo");
        list.add("qwerty");
        list.add("foo");
        list.add("");
        list.add("magic");
        list.add("haha");

        List<String> expectedList = new ArrayList<>();
        expectedList.add("abc");
        expectedList.add("oooooo");
        expectedList.add("qwerty");
        expectedList.add("foo");

        Predicate<String> notEmpty = new Predicate<String>() {
            @Override
            public Boolean apply(String str) {
                return str.length() > 0;
            }
        };

        ArrayList<String> res = (ArrayList<String>) Collections.takeWhile(notEmpty, list);

        assertEquals(expectedList.size(), res.size());

        for (int i = 0; i < res.size(); i++)
            assertTrue(res.get(i).equals(expectedList.get(i)));
    }

    /**
     * Testing of takeUnless method.
     */
    @Test
    public void takeUnlessNormalCase() throws Exception {
        List<Integer> list = new ArrayList<>();
        for (int i = 6; i < 15; i++)
            list.add(i);

        List<Integer> expectedList = new ArrayList<>();
        for (int i = 6; i < 11; i++)
            expectedList.add(i);

        Predicate<Integer> moreThen10 = new Predicate<Integer>() {
            @Override
            public Boolean apply(Integer x) {
                return 10 < x;
            }
        };

        ArrayList<Integer> res = (ArrayList<Integer>) Collections.takeUnless(moreThen10, list);

        assertEquals(expectedList.size(), res.size());

        for (int i = 0; i < res.size(); i++) {
            assertTrue(res.get(i).equals(expectedList.get(i)));
        }
    }

    /**
     * Testing of foldl method. Product of two elements.
     */
    @Test
    public void foldlProduct() throws Exception {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < 6; i++)
            list.add(i);

        Function2<Integer, Integer, Integer> prod = new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer x, Integer y) {
                return x * y;
            }
        };

        Integer res = Collections.foldl(prod, 1, list);

        assertTrue(res.equals(120));
    }

    /**
     * Testing of compose method. Concat strings (noncommutative operation).
     */
    @Test
    public void foldlConcat() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");

        Function2<String, String, String> concat = new Function2<String, String, String>() {
            @Override
            public String apply(String x, String y) {
                return x + y;
            }
        };

        String res = Collections.foldl(concat, "START", list);

        assertTrue(res.equals("STARTabcde"));
    }

    /**
     * Testing of foldr method. Concat strings (noncommutative operation).
     */
    @Test
    public void foldrConcat() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");

        Function2<String, String, String> concat = new Function2<String, String, String>() {
            @Override
            public String apply(String x, String y) {
                return x + y;
            }
        };

        String res = Collections.foldr(concat, "START", list);

        assertTrue(res.equals("abcdeSTART"));
    }
}