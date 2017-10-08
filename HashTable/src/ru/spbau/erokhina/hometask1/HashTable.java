package ru.spbau.erokhina.hometask1;

/**
 * A class that represents hash table -  data structure which implements an associative
 * array abstract data type, a structure that can map keys to values.
 */
public class HashTable {
    private List lists[];
    private int size;
    private int capacity;
    private static final long SHIFT = (long) 3e9;

    /**
     * Prints HashTable in order to debug.
     */
    public void printTable() {
        for (int i = 0; i < capacity; i++) {
            System.out.println(i);
            List tmp = lists[i].getNext();
            while (tmp != null) {
                System.out.println("key:");
                System.out.println(tmp.getPair().key());
                System.out.println("value:");
                System.out.println(tmp.getPair().value());

                tmp = tmp.getNext();
            }
        }
    }

    /**
     * Constructs new HashTable with default capacity 1.
     */
    public HashTable(){
        this(1);
    }

    /**
     * Constructs new HashTable with particular capacity.
     * @param newCapacity - number of lists.
     */
    public HashTable(int newCapacity){
        capacity = newCapacity;
        lists = new List[capacity];
        for (int i = 0; i < capacity; i++) {
            lists[i] = new List();
        }
    }

    /**
     * Returns the number of elements in HashTable.
     * @return the number of elements in HashTable.
     */
    public int size() {
        return size;
    }

    /**
     * Checks whether the HashTable contains the particular element.
     * @param key - key of the element that should be found.
     * @return true if element with given key was found, false otherwise.
     */
    public boolean contains(String key) {
        int pos = (int) ((key.hashCode() + SHIFT) % capacity);
        return (lists[pos].find(key) != null);
    }

    /**
     * Returns the value of given key if this pair is in HashTable and null otherwise.
     * @param key - key of the element that should be got.
     * @return value if element with given key was found, null otherwise.
     */
    public String get(String key) {
        int pos = (int) ((key.hashCode() + SHIFT) % capacity);

        if (!contains(key))
            return null;
        return lists[pos].find(key).value();
    }

    private void doubleIfNecessary() {
        if (size <= capacity)
            return;

        List newLists[] = new List[capacity * 2];
        for (int i = 0; i < 2 * capacity; i++) {
            newLists[i] = new List();
        }

        for (int i = 0; i < capacity; i++) {
            List tmp = lists[i].getNext();
            while (tmp != null) {
                int newPos = (int) ((tmp.getPair().key().hashCode() + SHIFT) % (2 * capacity));
                newLists[newPos].put(tmp.getPair().key(), tmp.getPair().value());
                tmp = tmp.getNext();
            }
        }
        lists = newLists;
        capacity *= 2;
    }

    /**
     * Adds key-value pair into HashTable if there is not any elements with given key.
     * Changes the value of the pair with this key otherwise.
     * @param key is the key of new element.
     * @param value is the value of new element.
     * @return element with the given key that was replaced by the new one (with new value).
     */
    public String put(String key, String value) {
        int pos = (int) ((key.hashCode() + SHIFT) % capacity);

        String prevValue = lists[pos].put(key, value);

        if (prevValue == null)
            size++;

        doubleIfNecessary();

        return prevValue;
    }

    /**
     * Tries to remove the element with given key. If such an element exists in HashTable it removes it and
     * returns the value of it. Otherwise it returns null.
     * @param key is the key of the element that should be deleted.
     * @return the value of the element that was deleted, null if it doesn't exist.
     */
    public String remove(String key) {
        int pos = (int) ((key.hashCode() + SHIFT) % capacity);

        if (contains(key))
            size--;
        return lists[pos].remove(key);
    }

    /**
     * Clears all HashTable (removes all elements).
     */
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            lists[i] = new List();
        }
        size = 0;
    }
}