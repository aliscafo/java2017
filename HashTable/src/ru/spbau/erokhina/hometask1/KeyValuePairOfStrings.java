package ru.spbau.erokhina.hometask1;

public class KeyValuePairOfStrings {
    private String key, value;

    public String key() {
        return key;
    }

    public String value() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public KeyValuePairOfStrings(String newKey, String newValue) {
        key = newKey;
        value = newValue;
    }
}
