package ru.spbau.erokhina.hometask1;

public class List {
    private KeyValuePairOfStrings pair;
    private List next;

    /**
     * Gets the element of the list.
     * @return the element of the list.
     */
    public KeyValuePairOfStrings getPair() {
        return pair;
    }

    /**
     * Gets the next element of the list.
     * @return the next element of the list.
     */
    public List getNext() {
        return next;
    }

    /**
     * Constructs new List instance with null pair and null next value.
     */
    public List () {
    }

    /**
     * Constructs new List instance with given pair and null next value.
     */
    public List(KeyValuePairOfStrings pair) {
        this.pair = pair;
        next = null;
    }

    /**
     * Returns the pair that has given key.
     * @param findKey is the key of the element that should be found.
     * @return the pair that has given key.
     */
    public KeyValuePairOfStrings find(String findKey) {
        List tmp = this.next;
        while (tmp != null) {
            if (tmp.pair.key() == findKey)
                return tmp.pair;
            tmp = tmp.next;
        }
        return null;
    }

    /**
     * Adds element with given key and value at the end of the list or change the value of the pair with the given
     * key if it exists.
     * @param key is the key of new element.
     * @param value id the value of new element.
     * @return previous value of the pair with the same key, null if it doesn't exist.
     */
    public String put(String key, String value) {
        List tmp = this.next;
        List prev = this;
        while (tmp != null) {
            if (tmp.pair.key() == key) {
                String prevValue = tmp.pair.value();
                tmp.pair.setValue(value);
                return prevValue;
            }
            prev = tmp;
            tmp = tmp.next;
        }
        prev.next = new List(new KeyValuePairOfStrings(key, value));
        return null;
    }

    /**
     * Removes the element with given key. Or does nothing it it doesn't exist.
     * @param key the key of the element that should be deleted.
     * @return the value of deleted element, null if it doesn't exist.
     */
    public String remove(String key) {
        List tmp = this.next, prev = this;
        while (tmp != null) {
            if (tmp.pair.key() == key) {
                String prevValue = tmp.pair.value();
                prev.next = tmp.next;
                return prevValue;
            }
            prev = tmp;
            tmp = tmp.next;
        }
        return null;
    }
}