package ru.spbau.erokhina.bstree.test;

import org.junit.Test;
import ru.spbau.erokhina.bstree.src.BSTree;

import static org.junit.Assert.*;

/**
 * Class for testing BSTree class.
 */
public class BSTreeTest {
    /**
     * Just normal case for checking add method (for Integer parameter).
     */
    @Test
    public void addNormalCaseInteger() {
        BSTree<Integer> tree = new BSTree();

        tree.add(5);
        assertTrue(tree.contains(5));

        tree.add(4);
        assertTrue(tree.contains(4));

        tree.add(1);
        assertTrue(tree.contains(1));
    }

    /**
     * Just normal case for checking add method (for String parameter).
     */
    @Test
    public void addNormalCaseString() {
        BSTree<String> tree = new BSTree();

        tree.add("A");
        assertTrue(tree.contains("A"));

        tree.add("B");
        assertTrue(tree.contains("B"));

        tree.add("C");
        assertTrue(tree.contains("C"));
    }

    /**
     * Tests add method with adding same elements.
     */
    @Test
    public void addSameElements() {
        BSTree<String> tree = new BSTree();

        tree.add("A");
        assertTrue(tree.contains("A"));

        tree.add("A");
        assertEquals(tree.size(), 1);
    }

    /**
     * Just normal case for checking contains method (for String parameter).
     */
    @Test
    public void containsNormalCase() {
        BSTree<String> tree = new BSTree();

        assertFalse(tree.contains("A"));
        assertFalse(tree.contains("B"));

        tree.add("A");
        tree.add("B");

        assertTrue(tree.contains("A"));
        assertTrue(tree.contains("B"));
    }

    /**
     * Just normal case for checking size method (for String parameter).
     */
    @Test
    public void sizeNormalCase() {
        BSTree<String> tree = new BSTree();

        assertEquals(0, tree.size());

        tree.add("A");
        assertEquals(1, tree.size());

        tree.add("B");
        assertEquals(2, tree.size());

        tree.add("A");
        assertEquals(2, tree.size());
    }
}