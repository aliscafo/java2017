package ru.spbau.erokhina;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Hash table and linked list implementation of the Map interface, with predictable iteration order.
 * @param <K> - the type of keys maintained by this map.
 * @param <V> - the type of mapped values.
 */
public class LinkedHashMap<K, V> extends AbstractMap<K, V> {
    private MySet<Entry<K, V>> myEntries;

    @Override
    public Set<Entry<K, V>> entrySet() {
        return myEntries;
    }

    public LinkedHashMap() {
        myEntries = new MySet<>();
    }

    public V put(K key, V value) {
        myEntries.add(new MyEntry<>(key, value));
        return value;
    }

    class MyEntry<K, V> implements Entry<K, V> {

        private K key;
        private V value;

        public MyEntry<K, V> getNext() {
            return next;
        }

        private MyEntry<K, V> next;

        public void setPrev(MyEntry<K, V> prev) {
            this.prev = prev;
        }

        public MyEntry<K, V> getPrev() {
            return prev;
        }

        private MyEntry<K, V> prev;

        public void setNextInGlobalList(MyEntry<K, V> nextInGlobalList) {
            this.nextInGlobalList = nextInGlobalList;
        }

        public MyEntry<K, V> getNextInGlobalList() {
            return nextInGlobalList;
        }

        private MyEntry<K, V> nextInGlobalList;

        public void setPrevInGlobalList(MyEntry<K, V> prevInGlobalList) {
            this.prevInGlobalList = prevInGlobalList;
        }

        private MyEntry<K, V> prevInGlobalList;

        public MyEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public void setNext(MyEntry<K, V> next) {
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            this.value = value;
            return value;
        }
    }

    class MySet<T extends Entry<K, V>> implements Set<T> {
        private static final int MOD = 100_000;
        private MyEntry<K, V>[] heads;
        private MyEntry<K, V>[] tails;
        private MyEntry<K, V> head;
        private MyEntry<K, V> tail;

        private int size;

        public MySet() {
            heads = (MyEntry<K, V>[])Array.newInstance(MyEntry.class, MOD);
            tails = (MyEntry<K, V>[])Array.newInstance(MyEntry.class, MOD);
        }

        @Override
        public int size() {
            return size;
        }

        @Override
        public boolean isEmpty() {
            return size == 0;
        }

        @Override
        public boolean contains(Object o) {
            return false;
        }

        @Override
        public Iterator<T> iterator() {
            return new MyIterator<>(head);
        }

        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T1> T1[] toArray(T1[] a) {
            return null;
        }

        @Override
        public boolean add(T t) {
            int hash = t.getKey().hashCode() % MOD;
            MyEntry<K, V> current = new MyEntry<>(t.getKey(), t.getValue());
            if (heads[hash] == null) {
                heads[hash] = tails[hash] = current;
            } else {
                tails[hash].setNext(current);
                current.setPrev(tails[hash]);
                tails[hash] = current;
            }
            if (head == null) {
                head = tail = current;
            } else {
                tail.setNext(current);
                current.setPrev(tail);
                tail = current;
            }
            return true;
        }

        @Override
        public boolean remove(Object o) {
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(Collection<? extends T> c) {
            return false;
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            return false;
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        public class MyIterator<T extends MyEntry<K, V>> implements Iterator {

            private MyEntry<K, V> currentPosition;

            public MyIterator(MyEntry<K, V> head) {
                currentPosition = head;
            }

            @Override
            public boolean hasNext() {
                return currentPosition != null;
            }

            @Override
            public T next() {
                T result = (T) currentPosition;
                currentPosition = currentPosition.getNext();
                return result;
            }
        }
    }
}

