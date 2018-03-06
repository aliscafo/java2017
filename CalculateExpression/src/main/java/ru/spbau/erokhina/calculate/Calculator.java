package ru.spbau.erokhina.calculate;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Class for transforming expression specified in infix notation to expression in postfix notation. Also can
 * calculate expression specified in postfix notation.
 */
public class Calculator {
    private Stack<Integer> stackInt;
    private Stack<Character> stackChar;

    private static final HashMap<Character, BiFunction<Integer, Integer, Integer>> operatorsMap;
    static {
        operatorsMap = new HashMap<>();
        operatorsMap.put('+', (x, y) -> x + y);
        operatorsMap.put('-', (x, y) -> x - y);
        operatorsMap.put('*', (x, y) -> x * y);
        operatorsMap.put('/', (x, y) -> x / y);
    }
    private static final HashMap<Character, Integer> priorities;
    static {
        priorities = new HashMap<>();
        priorities.put('(', 0);
        priorities.put('+', 1);
        priorities.put('-', 1);
        priorities.put('*', 2);
        priorities.put('/', 2);
    }

    /**
     * Transforms an expression in infix notation to postfix notation.
     * @param infixExpression given expression.
     * @return expression in postfix notation.
     */
    String toPostfix(String infixExpression) throws Exception {
        infixExpression = infixExpression.replaceAll(" ", "");
        StringBuilder postfixExpression = new StringBuilder();

        for (int i = 0; i < infixExpression.length(); i++) {
            Character token = infixExpression.charAt(i);

            if (Character.isDigit(token)) {
                postfixExpression.append(token);
            }
            else if (token.equals('(')) {
                stackChar.push(token);
            }
            else if (token.equals(')')) {
                while (true) {
                    Character top = stackChar.pop();
                    if (top.equals('('))
                        break;
                    postfixExpression.append(top);
                }
            }
            else {
                while (true) {
                    if (stackChar.size() == 0)
                        break;
                    Character top = stackChar.pop();
                    if (priorities.get(top) < priorities.get(token)) {
                        stackChar.push(top);
                        break;
                    }
                    postfixExpression.append(top);
                }
                stackChar.push(token);
            }
        }

        while (true) {
            if (stackChar.size() == 0)
                break;
            Character top = stackChar.pop();
            postfixExpression.append(top);
        }

        return postfixExpression.toString().replaceAll("", " ").trim();
    }

    /**
     * Constructor that gets two stacks that will be needed in basic methods.
     * @param newStackInt stack for storing integers.
     * @param newStackChar stack for storing characters.
     */
    Calculator(Stack<Integer> newStackInt, Stack<Character> newStackChar) {
        stackInt = newStackInt;
        stackChar = newStackChar;
    }

    /**
     * Calculates expression in postfix notation.
     * @param postfixExpression given expression.
     * @return the result of the expression.
     */
    int calculate (String postfixExpression) throws Exception {
        postfixExpression = postfixExpression.replaceAll(" ", "");

        for (int i = 0; i < postfixExpression.length(); i++) {
            Character token = postfixExpression.charAt(i);

            if (operatorsMap.containsKey(token)) {
                Integer b = stackInt.pop();
                Integer a = stackInt.pop();

                BiFunction<Integer, Integer, Integer> op = operatorsMap.get(token);

                stackInt.push((Integer) op.apply(a, b));
            }
            else {
                stackInt.push((Integer)(token - '0'));
            }
        }

        return stackInt.pop();
    }

    public static void main (String args[]) throws Exception {
        String action = args[0];
        String str = args[1];

        if (action.equals("") || str.equals("")) {
            System.out.println("Hi! Please, choose action:");
            System.out.println("---> Type \"toPostfix\" and an expression in infix notation to transform it to postfix notation.");
            System.out.println("---> Type \"calculate\" and an expression in postfix notation to calculate it.");
        }

        Stack<Integer> stackInt = new Stack<>();
        Stack<Character> stackChar = new Stack<>();
        Calculator calc = new Calculator(stackInt, stackChar);

        if (action.equals("toPostfix")) {
            System.out.println("toPostfix:");

            System.out.println(calc.toPostfix(str));
        }
        else if (action.equals("calculate")) {
            System.out.println("calculate:");

            System.out.println(calc.calculate(str));
        }
        else
            System.out.println("Unknown option. Try again!");
    }
}
