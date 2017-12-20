package ru.spbau.erokhina.cw3;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Class for testing SmartList class.
 */
public class SmartListTest {
    /**
     * Simple test.
     */
    @Test
    public void testSimple() {
        List<Integer> list = newList();

        assertEquals(Collections.<Integer>emptyList(), list);

        list.add(1);
        assertEquals(Collections.singletonList(1), list);

        list.add(2);
        assertEquals(Arrays.asList(1, 2), list);
    }

    /**
     * Testing get and set methods.
     */
    @Test
    public void testGetSet() {
        List<Object> list = newList();

        list.add(1);

        assertEquals(1, list.get(0));
        assertEquals(1, list.set(0, 2));
        assertEquals(2, list.get(0));
        assertEquals(2, list.set(0, 1));

        list.add(2);

        assertEquals(1, list.get(0));
        assertEquals(2, list.get(1));

        assertEquals(1, list.set(0, 2));

        assertEquals(Arrays.asList(2, 2), list);
    }

    /**
     * Testing remove method.
     */
    @Test
    public void testRemove() throws Exception {
        List<Object> list = newList();

        list.add(1);
        list.remove(0);
        assertEquals(Collections.emptyList(), list);

        list.add(2);
        list.remove((Object) 2);
        assertEquals(Collections.emptyList(), list);

        list.add(1);
        list.add(2);
        assertEquals(Arrays.asList(1, 2), list);

        list.remove(0);
        assertEquals(Collections.singletonList(2), list);

        list.remove(0);
        assertEquals(Collections.emptyList(), list);
    }

    /**
     * Testing removing with iterator.
     */
    @Test
    public void testIteratorRemove() throws Exception {
        List<Object> list = newList();
        assertFalse(list.iterator().hasNext());

        list.add(1);

        Iterator<Object> iterator = list.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());

        iterator.remove();
        assertFalse(iterator.hasNext());
        assertEquals(Collections.emptyList(), list);

        list.addAll(Arrays.asList(1, 2));

        iterator = list.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());

        iterator.remove();
        assertTrue(iterator.hasNext());
        assertEquals(Collections.singletonList(2), list);
        assertEquals(2, iterator.next());

        iterator.remove();
        assertFalse(iterator.hasNext());
        assertEquals(Collections.emptyList(), list);
    }

    /**
     * Testing constructor with collection.
     */
    @Test
    public void testCollectionConstructor() throws Exception {
        assertEquals(Collections.emptyList(), newList(Collections.emptyList()));
        assertEquals(
                Collections.singletonList(1),
                newList(Collections.singletonList(1)));

        assertEquals(
                Arrays.asList(1, 2),
                newList(Arrays.asList(1, 2)));
    }

    /**
     * Testing with many elements.
     */
    @Test
    public void testAddManyElementsThenRemove() throws Exception {
        List<Object> list = newList();
        for (int i = 0; i < 7; i++) {
            list.add(i + 1);
        }

        assertEquals(Arrays.asList(1, 2, 3, 4, 5, 6, 7), list);

        for (int i = 0; i < 7; i++) {
            list.remove(list.size() - 1);
            assertEquals(6 - i, list.size());
        }

        assertEquals(Collections.emptyList(), list);
    }

    /**
     * Testing of throwing IndexOutOfBoundsException.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testThrowingAnException() throws Exception {
        List<Integer> list = new SmartList<>();

        list.add(5);
        list.add(6);

        list.remove(3);
    }

    /**
     * Testing of constructor with arguments.
     */
    @Test
    public void testConstructorWithArguments() throws Exception {
        Collection<Integer> collection = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            collection.add(i + 1);;
        }

        List<Integer> list = new SmartList<>(collection);

        assertEquals(Arrays.asList(1, 2, 3, 4, 5), list);
    }

    /**
     * Complex test.
     */
    @Test
    public void testManyChangesOfList() throws Exception {
        List<Integer> list = new SmartList<>();
        for (int i = 0; i < 5; i++) {
            list.add(i + 1);
        }

        assertEquals(Arrays.asList(1, 2, 3, 4, 5), list);

        list.set(3, 400);
        assertEquals(Arrays.asList(1, 2, 3, 400, 5), list);

        list.set(4, 500);
        assertEquals(Arrays.asList(1, 2, 3, 400, 500), list);

        list.remove(0);
        assertEquals(Arrays.asList(2, 3, 400, 500), list);

        list.add(600);
        list.add(700);
        list.add(800);

        assertEquals(Arrays.asList(2, 3, 400, 500, 600, 700, 800), list);

        list.remove(2);
        list.remove(2);
        list.remove(2);
        assertEquals(Arrays.asList(2, 3, 700, 800), list);
    }
    
    /**
     * Testing of clear method.
     */
    @Test
    public void testClear() throws Exception {
        List<Integer> list = new SmartList<>();
        for (int i = 0; i < 5; i++) {
            list.add(i + 1);
        }

        list.clear();

        assertEquals(Collections.<Integer>emptyList(), list);
    }

    private static <T> List<T> newList() {
        try {
            return (List<T>) getListClass().getConstructor().newInstance();
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> List<T> newList(Collection<T> collection) {
        try {
            return (List<T>) getListClass().getConstructor(Collection.class).newInstance(collection);
        } catch (InstantiationException | ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private static Class<?> getListClass() throws ClassNotFoundException {
        return Class.forName("ru.spbau.erokhina.cw3.SmartList");
    }
}
