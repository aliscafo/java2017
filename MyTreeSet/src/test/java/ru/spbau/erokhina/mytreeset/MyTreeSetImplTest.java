package ru.spbau.erokhina.mytreeset;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Class for testing method of MyTreeSetImpl.
 */
public class MyTreeSetImplTest {
    private MyTreeSetImpl <Integer> tree;

    @Before
    public void before() {
        tree = new MyTreeSetImpl<>();
    }

    /**
     * Testing add method.
     */
    @Test
    public void testAdd() throws Exception {
        assertTrue(tree.add(3));
        assertTrue(tree.add(2));
        assertTrue(tree.add(1));

        assertFalse(tree.add(2));

        assertTrue(tree.contains(1));
        assertTrue(tree.contains(2));
        assertTrue(tree.contains(3));
    }

    /**
     * Testing remove method.
     */
    @Test
    public void testRemove() throws Exception {
        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        tree.add(5);

        assertTrue(tree.remove(3));
        assertFalse(tree.contains(3));

        assertTrue(tree.remove(2));
        assertFalse(tree.contains(2));

        assertTrue(tree.remove(5));
        assertFalse(tree.contains(5));

        assertFalse(tree.remove(3));
    }

    /**
     * Testing remove method.
     */
    @Test
    public void testComplexRemove() throws Exception {
        tree.add(2);
        tree.add(3);
        tree.add(1);

        assertTrue(tree.remove(2));
        assertFalse(tree.contains(2));

        assertTrue(tree.remove(1));
        assertFalse(tree.contains(1));
    }

    /**
     * Testing size method.
     */
    @Test
    public void testSize() throws Exception {
        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        tree.add(5);

        assertEquals(5, tree.size());
    }

    /**
     * Testing contains method.
     */
    @Test
    public void testContains() throws Exception {
        tree.add(1);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        tree.add(5);

        assertTrue(tree.contains(1));
        assertTrue(tree.contains(2));
        assertTrue(tree.contains(3));
        assertTrue(tree.contains(4));
        assertTrue(tree.contains(5));

        assertFalse(tree.contains(6));
    }

    /**
     * Testing higher method.
     */
    @Test
    public void testHigher() throws Exception {
        tree.add(1);
        tree.add(10);
        tree.add(100);
        tree.add(1000);
        tree.add(10000);

        assertTrue(tree.higher(5).equals(10));
        assertTrue(tree.higher(122).equals(1000));
        assertTrue(tree.higher(55).equals(100));
        assertTrue(tree.higher(5544).equals(10000));

        assertTrue(tree.higher(1).equals(10));
        assertTrue(tree.higher(100).equals(1000));
        assertTrue(tree.higher(10).equals(100));
        assertTrue(tree.higher(1000).equals(10000));
    }

    /**
     * Testing lower method.
     */
    @Test
    public void testLower() throws Exception {
        tree.add(1);
        tree.add(10);
        tree.add(100);
        tree.add(1000);
        tree.add(10000);

        assertTrue(tree.lower(5).equals(1));
        assertTrue(tree.lower(122).equals(100));
        assertTrue(tree.lower(55).equals(10));
        assertTrue(tree.lower(5544).equals(1000));

        assertTrue(tree.lower(10).equals(1));
        assertTrue(tree.lower(100).equals(10));
        assertTrue(tree.lower(1000).equals(100));
        assertTrue(tree.lower(10000).equals(1000));
    }

    /**
     * Testing floor method.
     */
    @Test
    public void testFloor() throws Exception {
        tree.add(1);
        tree.add(10);
        tree.add(100);
        tree.add(1000);
        tree.add(10000);

        assertTrue(tree.floor(5).equals(1));
        assertTrue(tree.floor(122).equals(100));
        assertTrue(tree.floor(55).equals(10));
        assertTrue(tree.floor(5544).equals(1000));

        assertTrue(tree.floor(10).equals(10));
        assertTrue(tree.floor(100).equals(100));
        assertTrue(tree.floor(1000).equals(1000));
        assertTrue(tree.floor(10000).equals(10000));
    }

    /**
     * Testing ceiling method.
     */
    @Test
    public void testCeiling() throws Exception {
        tree.add(1);
        tree.add(10);
        tree.add(100);
        tree.add(1000);
        tree.add(10000);

        assertTrue(tree.ceiling(5).equals(10));
        assertTrue(tree.ceiling(122).equals(1000));
        assertTrue(tree.ceiling(55).equals(100));
        assertTrue(tree.ceiling(5544).equals(10000));

        assertTrue(tree.ceiling(1).equals(1));
        assertTrue(tree.ceiling(100).equals(100));
        assertTrue(tree.ceiling(10).equals(10));
        assertTrue(tree.ceiling(1000).equals(1000));
    }

    /**
     * Testing iterator method.
     */
    @Test
    public void testIterator() throws Exception {
        tree.add(66);
        tree.add(44);
        tree.add(55);
        tree.add(11);
        tree.add(22);
        tree.add(33);

        int i = 1;
        Iterator<Integer> it = tree.iterator();

        for (; it.hasNext(); ) {
            assertTrue(it.hasNext());
            Integer elem = it.next();

            assertTrue(elem.equals(i * 11));
            i++;
        }
        assertFalse(it.hasNext());
    }

    /**
     * Testing descendingIterator method.
     */
    @Test
    public void testDescendingIterator() throws Exception {
        tree.add(66);
        tree.add(44);
        tree.add(55);
        tree.add(11);
        tree.add(22);
        tree.add(33);

        int i = 6;
        Iterator<Integer> it = tree.descendingIterator();

        for (; it.hasNext(); ) {
            assertTrue(it.hasNext());
            Integer elem = it.next();

            assertTrue(elem.equals(i * 11));
            i--;
        }
        assertFalse(it.hasNext());
    }

    /**
     * Testing first method.
     */
    @Test
    public void testFirst() throws Exception {
        tree.add(66);
        tree.add(44);
        tree.add(55);
        tree.add(11);
        tree.add(22);
        tree.add(33);

        assertTrue(tree.first().equals(11));
    }

    /**
     * Testing last method.
     */
    @Test
    public void testLast() throws Exception {
        tree.add(66);
        tree.add(44);
        tree.add(55);
        tree.add(11);
        tree.add(22);
        tree.add(33);

        assertTrue(tree.last().equals(66));
    }

    /**
     * Testing descendingSet method.
     */
    @Test
    public void testDescendingSet() throws Exception {
        tree.add(66);
        tree.add(44);
        tree.add(55);
        tree.add(11);
        tree.add(22);
        tree.add(33);

        MyTreeSet<Integer> descendingSet = tree.descendingSet();

        assertTrue(descendingSet.first().equals(66));
        assertTrue(descendingSet.last().equals(11));

        int i = 6;
        Iterator<Integer> it = descendingSet.iterator();

        for (; it.hasNext(); ) {
            assertTrue(it.hasNext());
            Integer elem = it.next();

            assertTrue(elem.equals(i*11));
            i--;
        }

        assertEquals(6, descendingSet.size());

        assertTrue(descendingSet.lower(50).equals(55));
        assertTrue(descendingSet.higher(50).equals(44));
    }
}