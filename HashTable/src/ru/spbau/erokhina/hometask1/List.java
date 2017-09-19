package ru.spbau.erokhina.hometask1;

public class List {
    private KeyValuePairOfStrings pair;
    private List next;

    public KeyValuePairOfStrings getPair() {
        return pair;
    }

    public List getNext() {
        return next;
    }

    public List () {
        pair = null;
        next = null;
    }

    public List(KeyValuePairOfStrings pair) {
        this.pair = pair;
        next = null;
    }

    public KeyValuePairOfStrings find(String findKey) {
        List tmp = this.next;
        while (tmp != null) {
            if (tmp.pair.key() == findKey)
                return tmp.pair;
            tmp = tmp.next;
        }
        return null;
    }

    public String put(String key, String value) {
        List tmp = this.next, prev = this;
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
