package ru.spbau.erokhina.bstree.test;

import org.junit.Before;
import org.junit.Test;
import ru.spbau.erokhina.bstree.src.BSTree;

import static org.junit.Assert.*;

/**
 * Class for testing BSTree class.
 */
public class BSTreeTest {
    private BSTree<Integer> treeInt;
    private BSTree<String> treeStr;

    @Before
    public void initTree() {
        treeInt = new BSTree<>();
        treeStr = new BSTree<>();
    }

    /**
     * Just normal case for checking add method (for Integer parameter).
     */
    @Test
    public void addNormalCaseInteger() {
        treeInt.add(5);
        assertTrue(treeInt.contains(5));

        treeInt.add(4);
        assertTrue(treeInt.contains(4));

        treeInt.add(1);
        assertTrue(treeInt.contains(1));
    }

    /**
     * Just normal case for checking add method (for String parameter).
     */
    @Test
    public void addNormalCaseString() {
        treeStr.add("A");
        assertTrue(treeStr.contains("A"));

        treeStr.add("B");
        assertTrue(treeStr.contains("B"));

        treeStr.add("C");
        assertTrue(treeStr.contains("C"));
    }

    /**
     * Tests add method with adding same elements.
     */
    @Test
    public void addSameElements() {
        treeStr.add("A");
        assertTrue(treeStr.contains("A"));

        treeStr.add("A");
        assertEquals(1, treeStr.size());
    }

    /**
     * Just normal case for checking contains method (for String parameter).
     */
    @Test
    public void containsNormalCase() {
        assertFalse(treeStr.contains("A"));
        assertFalse(treeStr.contains("B"));

        treeStr.add("A");
        treeStr.add("B");

        assertTrue(treeStr.contains("A"));
        assertTrue(treeStr.contains("B"));
    }

    /**
     * Just normal case for checking size method (for String parameter).
     */
    @Test
    public void sizeNormalCase() {
        assertEquals(0, treeInt.size());

        treeStr.add("A");
        assertEquals(1, treeStr.size());

        treeStr.add("B");
        assertEquals(2, treeStr.size());

        treeStr.add("A");
        assertEquals(2, treeStr.size());
    }
}