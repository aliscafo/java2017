package ru.spbau.erokhina.test3;

import ru.spbau.erokhina.hometask3.Trie;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import static org.junit.Assert.*;

public class TrieTest {
    /**
     * Checks correctness of add method.
     */
    @org.junit.Test
    public void addTest() throws Exception {
        Trie tr1 = new Trie();

        assertTrue(tr1.add("Test1"));
        assertEquals(1, tr1.size());
        assertTrue(tr1.contains("Test1"));

        assertTrue(tr1.add("Test2"));
        assertEquals(2, tr1.size());
        assertTrue(tr1.contains("Test2"));

        assertFalse(tr1.add("Test1"));
        assertEquals(2, tr1.size());
    }

    /**
     * Checks if contains method works well.
     */
    @org.junit.Test
    public void containsTest() throws Exception {
        Trie tr1 = new Trie();

        tr1.add("Test1");
        assertTrue(tr1.contains("Test1"));
        assertFalse(tr1.contains("Test12"));
        assertFalse(tr1.contains("Test"));

        tr1.remove("Test1");
        assertFalse(tr1.contains("Test1"));
    }

    /**
     * Checks if remove method is correct.
     */
    @org.junit.Test
    public void removeTest() throws Exception {
        Trie tr1 = new Trie();

        tr1.add("Test1");
        tr1.add("Test12");
        tr1.add("Test2");
        tr1.add("Test");

        assertTrue(tr1.remove("Test1"));
        assertTrue(tr1.remove("Test2"));
        assertFalse(tr1.remove("Test1"));

        assertFalse(tr1.contains("Test1"));
        assertFalse(tr1.contains("Test2"));
        assertTrue(tr1.contains("Test12"));
        assertTrue(tr1.contains("Test"));
    }

    /**
     * Checks correctness of the size method.
     */
    @org.junit.Test
    public void sizeTest() throws Exception {
        Trie tr1 = new Trie();

        assertEquals(0, tr1.size());

        tr1.add("Test1");
        tr1.add("Test12");
        tr1.add("Test2");
        tr1.add("Test");

        assertEquals(4, tr1.size());
        tr1.remove("Test2");
        tr1.remove("Test1");

        assertEquals(2, tr1.size());
    }

    /**
     * Checks if howManyStartsWithPrefix is correct.
     */
    @org.junit.Test
    public void howManyStartsWithPrefixTest() throws Exception {
        Trie tr1 = new Trie();

        assertEquals(0, tr1.howManyStartsWithPrefix(""));

        tr1.add("Test1");
        tr1.add("Test12");
        tr1.add("Test2");
        tr1.add("Test");

        assertEquals(4, tr1.howManyStartsWithPrefix(""));
        assertEquals(4, tr1.howManyStartsWithPrefix("Test"));
        assertEquals(2, tr1.howManyStartsWithPrefix("Test1"));
        assertEquals(1, tr1.howManyStartsWithPrefix("Test12"));

        tr1.remove("Test1");
        assertEquals(1, tr1.howManyStartsWithPrefix("Test1"));
    }

    /**
     * Checks both serialize and deserialize methods.
     */
    @org.junit.Test
    public void serializeAndDeserializeTest() throws Exception {
        Trie tr1 = new Trie();
        tr1.add("Test1");
        tr1.add("Test12");
        tr1.add("Test2");
        tr1.add("Test");

        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        tr1.serialize(byteArrayOut);

        Trie tr2 = new Trie();
        ByteArrayInputStream byteArrayIn = new ByteArrayInputStream(byteArrayOut.toByteArray());
        tr2.deserialize(byteArrayIn);

        assertEquals(4, tr2.size());
        assertTrue(tr2.contains("Test1"));
        assertTrue(tr2.contains("Test12"));
        assertTrue(tr2.contains("Test2"));
        assertTrue(tr2.contains("Test1"));
    }

}