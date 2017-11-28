package ru.spbau.erokhina.calculate;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Class for testing Stack class.
 */
public class StackTest {
    /**
     * Testing pop method.
     */
    @Test
    public void testPop() throws Exception {
        Stack<Integer> stack = new Stack<>();

        stack.push(1);
        stack.push(3);
        stack.push(5);
        stack.push(18);

        assertTrue(stack.pop().equals(18));
        assertEquals(3, stack.size());

        assertTrue(stack.pop().equals(5));
        assertEquals(2, stack.size());

        assertTrue(stack.pop().equals(3));
        assertEquals(1, stack.size());

        assertTrue(stack.pop().equals(1));
        assertEquals(0, stack.size());
    }

    /**
     * Testing push method.
     */
    @Test
    public void testPush() throws Exception {
        Stack<Integer> stack = new Stack<>();

        stack.push(1);
        assertEquals(1, stack.size());

        stack.push(3);
        assertEquals(2, stack.size());

        stack.push(5);
        assertEquals(3, stack.size());

        stack.push(18);
        assertEquals(4, stack.size());
    }
}