package ru.spbau.erokhina.hometask2;

import static org.junit.Assert.*;
import org.junit.Test;
import ru.spbau.erokhina.hometask1.HashTable;

public class HashTableTest {
    /**
     * Checks correctness of the size method.
     */
    @Test
    public void size() throws Exception {
        HashTable ht1 = new HashTable();
        assertEquals(0, ht1.size());
        ht1.put("keyA", "A");
        assertEquals(1, ht1.size());
        ht1.put("keyB", "B");
        assertEquals(2, ht1.size());
        ht1.put("keyA", "A2");
        assertEquals(2, ht1.size());
        ht1.remove("keyA");
        assertEquals(1, ht1.size());
    }

    /**
     * Checks correctness of the contains method.
     */
    @Test
    public void contains() throws Exception {
        HashTable ht1 = new HashTable();
        assertEquals(false, ht1.contains("keyA"));
        ht1.put("keyA", "A");
        ht1.put("keyB", "B");
        assertEquals(true, ht1.contains("keyA"));
        ht1.remove("keyA");
        assertEquals(false, ht1.contains("keyA"));
        ht1.put("keyB", "B2");
        assertEquals(true, ht1.contains("keyB"));
    }

    /**
     * Checks correctness of the get method.
     */
    @Test
    public void get() throws Exception {
        HashTable ht1 = new HashTable();
        assertEquals(null, ht1.get("keyA"));
        ht1.put("keyA", "A");
        ht1.put("keyB", "B");
        assertEquals("A", ht1.get("keyA"));
        ht1.remove("keyA");
        assertEquals(null, ht1.get("keyA"));
        ht1.put("keyB", "B2");
        assertEquals("B2", ht1.get("keyB"));
    }

    /**
     * Checks correctness of the put method.
     */
    @Test
    public void put() throws Exception {
        HashTable ht1 = new HashTable();
        assertEquals(null, ht1.put("keyA", "A"));
        assertEquals("A", ht1.get("keyA"));
        ht1.put("keyB", "B");
        ht1.put("keyC", "C");
        ht1.put("keyD", "D");
        ht1.put("keyE", "E");
        assertEquals("A", ht1.get("keyA"));
        assertEquals("B", ht1.get("keyB"));
        assertEquals("C", ht1.get("keyC"));
        assertEquals("D", ht1.get("keyD"));
        assertEquals("E", ht1.get("keyE"));
        assertEquals("A", ht1.put("keyA", "A2"));
        assertEquals("A2", ht1.get("keyA"));
    }

    /**
     * Checks correctness of the remove method.
     */
    @Test
    public void remove() throws Exception {
        HashTable ht1 = new HashTable();
        assertEquals(null, ht1.remove("keyA"));
        ht1.put("keyA", "A");
        ht1.put("keyB", "B");
        ht1.put("keyC", "C");
        ht1.put("keyD", "D");
        ht1.put("keyE", "E");
        assertEquals("A", ht1.remove("keyA"));
        assertEquals(4, ht1.size());
        assertEquals("B", ht1.remove("keyB"));
        assertEquals("C", ht1.remove("keyC"));
        assertEquals("D", ht1.remove("keyD"));
        assertEquals("E", ht1.remove("keyE"));
        assertEquals(0, ht1.size());
    }

    /**
     * Checks correctness of the clear method.
     */
    @Test
    public void clear() throws Exception {
        HashTable ht1 = new HashTable();
        ht1.put("keyA", "A");
        ht1.put("keyB", "B");
        ht1.put("keyC", "C");
        ht1.put("keyD", "D");
        ht1.put("keyE", "E");
        ht1.clear();
        assertEquals(0, ht1.size());
        assertEquals(false, ht1.contains("keyA"));
    }
}