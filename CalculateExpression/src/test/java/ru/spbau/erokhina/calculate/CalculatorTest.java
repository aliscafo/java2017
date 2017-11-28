package ru.spbau.erokhina.calculate;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Class for testing Calculator class.
 */
public class CalculatorTest {
    /**
     * Simply testing toPostfix method.
     */
    @Test
    public void testToPostfix() throws Exception {
        String str = "2 * 3 * 4 + (5 * 6 + 7 / 8) * 9";
        Stack<Integer> stackInt = new Stack<>();
        Stack<Character> stackChar = new Stack<>();

        Calculator calc = new Calculator(stackInt, stackChar);

        assertEquals("2 3 * 4 * 5 6 * 7 8 / + 9 * +", calc.toPostfix(str));
    }

    /**
     * Simply testing calculate method.
     */
    @Test
    public void testCalculate() throws Exception {
        String str = "2 3 * 4 * 5 6 * 8 2 / + 9 * +";
        Stack<Integer> stackInt = new Stack<>();
        Stack<Character> stackChar = new Stack<>();

        Calculator calc = new Calculator(stackInt, stackChar);

        assertEquals(330, calc.calculate(str));
    }

    /**
     * Testing toPostfix method with mock-object (simple case).
     */
    @Test
    public void testToPostfixMock() throws Exception {
        String str = "2 + 3";
        Stack<Integer> stackInt = mock(Stack.class);
        Stack<Character> stackChar = mock(Stack.class);

        Calculator calc = new Calculator(stackInt, stackChar);

        when(stackChar.size()).thenReturn(0).thenReturn(1).thenReturn(0);
        when(stackChar.pop()).thenReturn('+');

        assertEquals("2 3 +", calc.toPostfix(str));
        verify(stackChar, times(1)).push(anyObject());
    }

    /**
     * Testing calculate method with mock-object (simple case).
     */
    @Test
    public void testCalculateMock() throws Exception {
        String str = "2 3 +";
        Stack<Integer> stackInt = mock(Stack.class);
        Stack<Character> stackChar = mock(Stack.class);

        Calculator calc = new Calculator(stackInt, stackChar);

        when(stackInt.pop()).thenReturn(1).thenReturn(2).thenReturn(5);

        assertEquals(5, calc.calculate(str));
        verify(stackInt, times(3)).push(anyObject());
    }

    /**
     * Testing toPostfix method with mock-object (with parenthesis).
     */
    @Test
    public void testToPostfixMockParenthesis() throws Exception {
        String str = "(6 + 3) * 5";
        Stack<Integer> stackInt = mock(Stack.class);
        Stack<Character> stackChar = mock(Stack.class);

        Calculator calc = new Calculator(stackInt, stackChar);

        when(stackChar.size()).thenReturn(0).thenReturn(0).thenReturn(1).thenReturn(0);
        when(stackChar.pop()).thenReturn('+').thenReturn('(').thenReturn('*');

        assertEquals("6 3 + 5 *", calc.toPostfix(str));
        verify(stackChar, times(3)).push(anyObject());
    }

    /**
     * Testing calculate method with mock-object (with parenthesis).
     */
    @Test
    public void testCalculateMockParenthesis() throws Exception {
        String str = "6 3 + 5 *";
        Stack<Integer> stackInt = mock(Stack.class);
        Stack<Character> stackChar = mock(Stack.class);

        Calculator calc = new Calculator(stackInt, stackChar);

        when(stackInt.pop()).thenReturn(3).thenReturn(6).thenReturn(5).thenReturn(9).thenReturn(45);

        assertEquals(45, calc.calculate(str));
        verify(stackInt, times(5)).push(anyObject());
    }
}