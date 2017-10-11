package ru.spbau.erokhina;

import org.junit.Test;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;

import static org.junit.Assert.*;

public class LinkedHashMapTest {
    /**
     * Normal case of LinkedHashMap's work.
     */
    @Test
    public void normalCase() throws Exception {
        LinkedHashMap<Integer, Integer> myMap = new LinkedHashMap<>();
        myMap.put(10, 20);
        myMap.put(20, 30);
        myMap.put(70, 100);
        Iterator<Map.Entry<Integer, Integer>> iterator = myMap.entrySet().iterator();

        Map.Entry<Integer, Integer> entry = iterator.next();
        assertTrue(entry.getKey().equals(10));
        assertTrue(entry.getValue().equals(20));

        entry = iterator.next();
        assertTrue(entry.getKey().equals(20));
        assertTrue(entry.getValue().equals(30));

        entry = iterator.next();
        assertTrue(entry.getKey().equals(70));
        assertTrue(entry.getValue().equals(100));
    }
}