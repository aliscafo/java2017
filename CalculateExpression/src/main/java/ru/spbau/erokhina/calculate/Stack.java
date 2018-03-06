package ru.spbau.erokhina.calculate;

import java.util.ArrayList;

/**
 * Class that represents stack data structure.
 * @param <T> the type of stored elements.
 */
public class Stack<T> {
    private ArrayList<T> data = new ArrayList<>();

    /**
     * Removes element from stack.
     * @return removed element.
     * @throws Exception if there is no elements for removing.
     */
    T pop () throws Exception {
        if (data.size() == 0)
            throw new Exception("No elements in stack.");

        int last = data.size() - 1;
        return data.remove(last);
    }

    /**
     * Adds element to the stack.
     * @param newEl new element.
     */
    void push (T newEl) {
        data.add(newEl);
    }

    /**
     * Returns the size of the stack.
     * @return the size of the stack.
     */
    int size () {
        return data.size();
    }
}
