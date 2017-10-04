package ru.spbau.erokhina.hometask3;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that represents an ordered tree data structure that is used to store a dynamic set
 * where the keys are strings.
 */
public class Trie implements Serializable {
    private Vertex root = new Vertex();
    private int size;

    /**
     * Adds element to this trie.
     * @param element - string that should be added.
     * @return true if there wasn't such an element before. Otherwise returns false.
     */
    public boolean add(String element) {
        if (element == null) {
            throw new IllegalArgumentException("Argument \"element\" is null.");
        }
        boolean result = root.add(element, 0);
        if (result) {
            size++;
        }
        return result;
    }

    /**
     * Checks if trie contains given element.
     * @param element - string that should be checked.
     * @return true if the element is in trie. False otherwise.
     */
    public boolean contains(String element) {
        if (element == null) {
            throw new IllegalArgumentException("Argument \"element\" is null.");
        }
        boolean result = root.contains(element, 0);
        return result;
    }

    /**
     * Removes given element from the trie.
     * @param element - string that should be deleted.
     * @return true if string was in the trie. False otherwise.
     */
    public boolean remove(String element) {
        if (element == null) {
            throw new IllegalArgumentException("Argument \"element\" is null.");
        }
        boolean result = root.remove(element, 0);
        if (result)
            size--;
        return result;
    }

    /**
     * Determines the number of elements in the trie.
     * @return the number of different elements (terminal nodes).
     */
    public int size() {
        return size;
    }

    /**
     * Calculates the number of different elements with given prefix.
     * @param prefix - given prefix.
     * @return the number of different elements with given prefix.
     */
    public int howManyStartsWithPrefix(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("Argument \"element\" is null.");
        }
        return root.findPrefixSize(prefix, 0);
    }

    /**
     * Serializes this trie to the given stream.
     * @param out - output stream.
     */
    public void serialize(OutputStream out) throws IOException {
        ObjectOutputStream outObject = new ObjectOutputStream(out);
        outObject.writeObject(root);
        outObject.writeObject(size);
    }

    /**
     * Deserializes data from given stream to the trie.
     * @param in - input stream.
     */
    public void deserialize(InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream inObject = new ObjectInputStream(in);
        root = (Vertex)inObject.readObject();
        size = (int)inObject.readObject();
    }

    private static class Vertex implements Serializable {
        private Map<Character, Vertex> next = new HashMap<>();
        private boolean isTerminal;
        private int size;

        public int getSize() {
            return size;
        }

        boolean add(String element, int index) {
            if (index == element.length()) {
                if (isTerminal)
                    return false;
                isTerminal = true;
                size++;
                return true;
            }

            Character curChar = element.charAt(index);
            if (next.containsKey(curChar) == false) {
                next.put(curChar, new Vertex());
            }
            boolean result = next.get(curChar).add(element, index + 1);
            if (result)
                size++;
            return result;
        }

        public boolean contains (String element, int index) {
            if (index == element.length()) {
                return isTerminal;
            }

            Character curChar = element.charAt(index);
            if (next.containsKey(curChar) == false) {
                return false;
            }
            return next.get(curChar).contains(element, index + 1);
        }

        public boolean remove (String element, int index) {
            if (index == element.length()) {
                if (isTerminal) {
                    isTerminal = false;
                    size--;
                    return true;
                }
                return false;
            }
            Character curChar = element.charAt(index);
            if (next.containsKey(curChar) == false) {
                return false;
            }
            boolean result = next.get(curChar).remove(element, index + 1);
            if (result)
                size--;
            return result;
        }

        public int findPrefixSize(String prefix, int index) {
            if (index == prefix.length()) {
                return size;
            }
            Character curChar = prefix.charAt(index);
            if (next.containsKey(curChar) == false) {
                return 0;
            }
            return next.get(curChar).findPrefixSize(prefix, index + 1);
        }
    }
}
