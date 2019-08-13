package ru.spbau.erokhina.cw3;

import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Mutable list that is optimized to store small number of elements. Its behaviour is similar to ArrayList.
 */
public class SmartList<E> extends AbstractList<E> implements List<E> {
    private int size;
    private Object data;

    /**
     * Creates an empty list.
     */
    public SmartList() {}

    /**
     * Creates a list with elements from the given collection.
     * @param collection given collection
     */
    public SmartList(Collection<? extends E> collection) {
        if (collection.size() == 0) {
            data = null;
            size = 0;
        }
        else if (collection.size() == 1) {
            data = collection.iterator().next();
            size = 1;
        }
        else if (2 <= collection.size() && collection.size() <= 5) {
            data = (E[])new Object[5];
            size = collection.size();

            int i = 0;
            for (E elem : collection) {
                ((E[])data)[i] = elem;
                i++;
            }
        }
        else {
            data = new ArrayList<E>();
            size = collection.size();

            for (E elem : collection) {
                ((ArrayList<E>)data).add(elem);
            }
        }
    }

    /**
     * Returns the element at the specified position in this list.
     * @param index index of the element to return
     * @return the element at the specified position in this list
     */
    @Override
    public E get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        if (size() == 1) {
            return (E)data;
        }
        if (2 <= size() && size() <= 5) {
            return ((E[])data)[index];
        }
        return ((ArrayList<E>)data).get(index);
    }

    /**
     * Appends the specified element to the end of this list.
     * @param e element to be appended to this list
     * @return true if this collection changed as a result of the call
     */
    @Override
    public boolean add(E e) {
        if (size() == 0) {
            data = e;
        }
        else if (size() == 1) {
            E savedValue = (E)data;

            data = (E[])new Object[5];
            ((E[])data)[0] = savedValue;
            ((E[])data)[1] = e;
        }
        else if (size() < 5) {
            ((E[])data)[size()] = e;
        }
        else if (size() == 5) {
            E[] savedArray = (E[])new Object[5];
            System.arraycopy(((E[])data), 0, savedArray, 0, 5);

            data = new ArrayList<E>();
            for (int i = 0; i < 5; i++) {
                ((ArrayList<E>)data).add(savedArray[i]);
            }
            ((ArrayList<E>)data).add(e);
        }
        else {
            ((ArrayList<E>)data).add(e);
        }

        size++;
        return true;
    }

    /**
     * Replaces the element at the specified position in this list with the specified element.
     * @param index index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     */
    @Override
    public E set(int index, E element) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        E previous;
        if (size() == 1) {
            previous = (E)data;
            data = element;
        }
        else if (2 <= size() && size() <= 5) {
            previous = ((E[])data)[index];
            ((E[])data)[index] = element;
        }
        else {
            previous = ((ArrayList<E>) data).get(index);
            ((ArrayList<E>) data).set(index, element);
        }

        return previous;
    }

    /**
     * Removes the element at the specified position in this list. Shifts any subsequent elements to the left
     * (subtracts one from their indices).
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= size())
     */
    @Override
    public E remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException();
        }
        E previous;
        if (size() == 1) {
            previous = (E)data;
            data = null;
        }
        else if (2 <= size() && size() <= 5) {
            previous = ((E[])data)[index];
            for (int i = index; i < size() - 1; i++) {
                ((E[])data)[i] = ((E[])data)[i + 1];
            }
            if (size() == 2)
                data = ((E[])data)[0];
        }
        else {
            previous = previous = ((ArrayList<E>) data).get(index);
            ((ArrayList<E>) data).remove(index);
            if (size() == 6)
                data = (E[])((ArrayList<E>) data).toArray();
        }

        size--;
        return previous;
    }

    /**
     * Returns the number of elements in this list.
     * @return the number of elements in this list
     */
    @Override
    public int size() {
        return size;
    }
}
