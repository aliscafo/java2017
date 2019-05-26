package ru.spbau.erokhina.mytreeset;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Implementation of MyTreeSet interface.
 * @param <E> type of elements in set.
 */
public class MyTreeSetImpl<E> extends AbstractSet<E> implements MyTreeSet<E> {
    private Node<E> root;
    private Node<E> minNode;
    private Node<E> maxNode;
    private int size;

    private Comparator<? super E> comparator;

    private static class Node<E> {
        private Node<E> left;
        private Node<E> right;
        private Node<E> parent;
        private E value;

        Node(E newValue) {
            value = newValue;
        }

        E getValue () {
            return value;
        }

        public Node<E> getLeft() {
            return left;
        }

        public Node<E> getRight() {
            return right;
        }

        public Node<E> getParent() {
            return parent;
        }

        public void setLeft(Node<E> l) {
            left = l;
        }

        public void setRight(Node<E> r) {
            right = r;
        }

        public void setParent(Node<E> pr) {
            parent = pr;
        }

        public void setValue(E newValue) {
            value = newValue;
        }
    }

    /**
     * Constructor without parameters.
     */
    public MyTreeSetImpl() {
        comparator = (a, b) -> ((Comparable)a).compareTo(b);
    }

    /**
     * Constructor with one parameter - comparator.
     * @param comp comparator.
     */
    public MyTreeSetImpl(Comparator<? super E> comp) {
        comparator = comp;
    }

    private Node<E> minSubtree(Node<E> subTree) {
        Node<E> cur = subTree;

        while (cur.getLeft() != null) {
            cur = cur.getLeft();
        }

        return cur;
    }

    private Node<E> maxSubtree(Node<E> subTree) {
        Node<E> cur = subTree;

        while (cur.getRight() != null) {
            cur = cur.getRight();
        }

        return cur;
    }

    /**
     * Adds element to the set.
     * @param e element that should be added to the set.
     * @return true if element was added. False if it is already in the set.
     */
    @Override
    public boolean add(E e) {
        if (maxNode == null || comparator.compare(e, maxNode.getValue()) > 0) {
            Node<E> newNode = new Node<>(e);
            maxNode = newNode;
        }

        if (minNode == null || comparator.compare(e, minNode.getValue()) < 0) {
            Node<E> newNode = new Node<>(e);
            minNode = newNode;
        }

        if (root == null) {
            Node<E> newNode = new Node<>(e);
            root = newNode;
            size++;
            return true;
        }

        Node<E> cur = root;

        while (true) {
            final int resOfCompare = comparator.compare(cur.getValue(), e);

            if (resOfCompare == 0) {
                return false;
            } else if (resOfCompare > 0) {
                if (cur.getLeft() == null) {
                    Node<E> newNode = new Node<>(e);
                    cur.setLeft(newNode);
                    newNode.setParent(cur);
                    size++;
                    return true;
                }
                cur = cur.getLeft();
            } else {
                if (cur.getRight() == null) {
                    Node<E> newNode = new Node<>(e);
                    cur.setRight(newNode);
                    newNode.setParent(cur);
                    size++;
                    return true;
                }
                cur = cur.getRight();
            }
        }
    }

    private Node<E> recRemove(Node<E> root, Object obj) {
        if (root == null) {
            return root;
        }

        if (comparator.compare(root.getValue(), (E) obj) > 0) {
            Node<E> l = recRemove(root.getLeft(), obj);
            root.setLeft(l);
            if (l != null)
                l.setParent(root);
        }
        else if (comparator.compare(root.getValue(), (E) obj) < 0) {
            Node<E> r = recRemove(root.getRight(), obj);
            root.setRight(r);
            if (r != null)
                r.setParent(root);
        }
        else if (root.getLeft() != null && root.getRight() != null) {
            root.setValue(minSubtree(root.getRight()).getValue());
            Node<E> r = recRemove(root.getRight(), root.getValue());
            root.setRight(r);
            if (r != null)
                r.setParent(root);
        }
        else {
            if (root.getLeft() != null) {
                root = root.getLeft();
            }
            else {
                root = root.getRight();
            }
        }

        return root;
    }

    /**
     * Removes element from the set.
     * @param obj element that shold be removed.
     * @return true if the element was removed from the set. False if it wasn't there.
     */
    @Override
    public boolean remove(Object obj) {
        if (!contains((E) obj))
            return false;
        size--;
        recRemove(root, (E) obj);
        return true;
    }

    /**
     * Returns an iterator over the elements in this set in ascending order.
     * @return an iterator over the elements in this set in ascending order.
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> curNode = minNode;

            @Override
            public boolean hasNext() {
                return (curNode != null);
            }

            @Override
            public E next() {
                E ans = null;
                if (hasNext())
                    ans = curNode.getValue();
                else
                    return null;

                if (curNode.getRight() != null) {
                    curNode = minSubtree(curNode.getRight());
                    return ans;
                }
                Node goUpPar = curNode.getParent();
                Node goUpCur = curNode;

                while (goUpPar != null && goUpPar.getRight() == goUpCur) {
                    goUpCur = goUpPar;
                    goUpPar = goUpPar.getParent();
                }

                curNode = goUpPar;

                return ans;
            }
        };
    }

    /**
     * Returns an iterator over the elements in this set in descending order.
     * @return an iterator over the elements in this set in descending order.
     */
    @Override
    public Iterator<E> descendingIterator() {
        return new Iterator<E>() {
            private Node<E> curNode = maxNode;

            @Override
            public boolean hasNext() {
                return (curNode != null);
            }

            @Override
            public E next() {
                E ans = null;
                if (hasNext())
                    ans = curNode.getValue();
                else
                    return null;

                if (curNode.getLeft() != null) {
                    curNode = maxSubtree(curNode.getLeft());
                    return ans;
                }
                Node goUpPar = curNode.getParent();
                Node goUpCur = curNode;

                while (goUpPar != null && goUpPar.getLeft() == goUpCur) {
                    goUpCur = goUpPar;
                    goUpPar = goUpPar.getParent();
                }

                curNode = goUpPar;

                return ans;
            }
        };
    }

    /**
     * Returns the number of elements in this set (its cardinality).
     * @return the number of elements in this set (its cardinality).
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns a reverse order view of the elements contained in this set.
     * @return a reverse order view of the elements contained in this set.
     */
    @Override
    public MyTreeSet<E> descendingSet() {
        return new MyTreeSet<E>() {
            @Override
            public Iterator<E> descendingIterator() {
                return MyTreeSetImpl.this.iterator();
            }

            @Override
            public MyTreeSet<E> descendingSet() {
                return MyTreeSetImpl.this;
            }

            @Override
            public E first() {
                return MyTreeSetImpl.this.last();
            }

            @Override
            public E last() {
                return MyTreeSetImpl.this.first();
            }

            @Override
            public E lower(E e) {
                return MyTreeSetImpl.this.higher(e);
            }

            @Override
            public E floor(E e) {
                return MyTreeSetImpl.this.ceiling(e);
            }

            @Override
            public E ceiling(E e) {
                return MyTreeSetImpl.this.floor(e);
            }

            @Override
            public E higher(E e) {
                return MyTreeSetImpl.this.lower(e);
            }

            @Override
            public int size() {
                return MyTreeSetImpl.this.size();
            }

            @Override
            public boolean isEmpty() {
                return MyTreeSetImpl.this.isEmpty();
            }

            @Override
            public boolean contains(Object o) {
                return MyTreeSetImpl.this.contains(o);
            }

            @Override
            public Iterator<E> iterator() {
                return MyTreeSetImpl.this.descendingIterator();
            }

            @Override
            public Object[] toArray() {
                return MyTreeSetImpl.this.toArray();
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return MyTreeSetImpl.this.toArray(a);
            }

            @Override
            public boolean add(E e) {
                return MyTreeSetImpl.this.add(e);
            }

            @Override
            public boolean remove(Object o) {
                return MyTreeSetImpl.this.remove(o);
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return MyTreeSetImpl.this.containsAll(c);
            }

            @Override
            public boolean addAll(Collection<? extends E> c) {
                return MyTreeSetImpl.this.addAll(c);
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return MyTreeSetImpl.this.retainAll(c);
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return MyTreeSetImpl.this.removeAll(c);
            }

            @Override
            public void clear() {
                MyTreeSetImpl.this.clear();
            }
        };
    }

    /**
     * Returns the first (lowest) element currently in this set.
     * @return the first (lowest) element currently in this set.
     */
    @Override
    public E first() {
        return minNode.getValue();
    }

    /**
     * Returns the last (highest) element currently in this set.
     * @return the last (highest) element currently in this set.
     */
    @Override
    public E last() {
        return maxNode.getValue();
    }

    private E recLower(Node<E> cur, E e) {
        if (cur == null)
            return null;

        if (comparator.compare(cur.getValue(), e) >= 0)
            return recLower(cur.getLeft(), e);

        E rightValue = recLower(cur.getRight(), e);

        if (rightValue == null)
            return cur.getValue();
        else
            return rightValue;
    }

    /**
     * Returns the greatest element in this set strictly less than the given element,
     * or null if there is no such element.
     * @param e a given element.
     * @return the greatest element in this set strictly less than the given element, or null if there is no such
     * element.
     */
    @Override
    public E lower(E e) {
        return recLower(root, e);
    }

    /**
     * Returns true if this set contains the specified element.
     * @param o specified element.
     * @return true if this set contains the specified element.
     */
    @Override
    public boolean contains(Object o) {
        return (findPrecise((E) o) != null);
    }

    private Node<E> findPrecise (E e) {
        Node<E> cur = root;

        while (true) {
            if (cur == null)
                return null;
            if (comparator.compare(cur.getValue(), e) == 0)
                return cur;
            if (comparator.compare(cur.getValue(), e) > 0)
                cur = cur.getLeft();
            else
                cur = cur.getRight();
        }
    }

    /**
     * Returns the greatest element in this set less than or equal to the given element, or null if
     * there is no such element.
     * @param e given element.
     * @return the greatest element in this set less than or equal to the given element, or null if there
     * is no such element.
     */
    @Override
    public E floor(E e) {
        if (contains(e))
            return e;
        return lower(e);
    }

    /**
     * Returns the least element in this set greater than or equal to the given element, or null if there is
     * no such element.
     * @param e given element.
     * @return the least element in this set greater than or equal to the given element, or null if there is
     * no such element.
     */
    @Override
    public E ceiling(E e) {
        if (contains(e))
            return e;
        return higher(e);
    }

    private E recHigher(Node<E> cur, E e) {
        if (cur == null)
            return null;

        if (comparator.compare(cur.getValue(), e) <= 0)
            return recHigher(cur.getRight(), e);

        E leftValue = recHigher(cur.getLeft(), e);

        if (leftValue == null)
            return cur.getValue();
        else
            return leftValue;
    }

    /**
     * Returns the least element in this set strictly greater than the given element, or null if there is
     * no such element.
     * @param e given element.
     * @return the least element in this set strictly greater than the given element, or null if there is
     * no such element.
     */
    @Override
    public E higher(E e) {
        return recHigher(root, e);
    }
}
