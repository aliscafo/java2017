package ru.spbau.erokhina.bstree.src;

/**
 * Class that represent Binary Search Tree.
 * @param <T> - type of elements in tree.
 */
public class BSTree<T extends Comparable> {
    private Node root;
    private int size;

    private class Node {
        private T value;
        private Node left;
        private Node right;

        Node(T newValue, Node newLeft, Node newRight) {
            value = newValue;
            left = newLeft;
            right = newRight;
        }
    }

    /**
     * Adds given element to the tree. If it exists, does nothing.
     * @param newEl - given element.
     * @return element that was added.
     */
    public T add(T newEl) {
        if (contains(newEl)) {
            return newEl;
        }
        size++;

        if (root == null) {
            root = new Node(newEl, null, null);
            return newEl;
        }

        Node tmp = root, prev = null;
        Boolean isLeft = false;

        while (tmp != null) {
            prev = tmp;
            if (tmp.value.compareTo(newEl) > 0) {
                tmp = tmp.left;
                isLeft = true;
            }
            else {
                tmp = tmp.right;
            }
        }

        tmp = new Node(newEl, null, null);

        if (isLeft) {
            prev.left = tmp;
        } else {
            prev.right = tmp;
        }

        return newEl;
    }

    /**
     * Returns true if given element is in tree. False otherwise.
     * @param element - element, existence of what we want to check.
     * @return true if given element is in tree. False otherwise.
     */
    public Boolean contains (T element) {
        Node tmp = root;
        while (tmp != null && tmp.value != element) {
            if (tmp.value.compareTo(element) > 0) {
                tmp = tmp.left;
            }
            else {
                tmp = tmp.right;
            }
        }
        return tmp != null;
    }

    /**
     * Returns number of elements in tree.
     * @return number of elements in tree.
     */
    public int size() {
        return size;
    }
}
