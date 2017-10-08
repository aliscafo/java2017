package ru.spbau.erokhina.hometask1;

public class KeyValuePairOfStrings {
    private String key, value;

    /**
     * Returns value of the key field.
     * @return value of the key field.
     */
    public String key() {
        return key;
    }

    /**
     * Returns the value field.
     * @return the value field.
     */
    public String value() {
        return value;
    }

    /**
     * Set the value field.
     * @param value is the new value.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Set the key field.
     * @param key is the new key.
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Constructs the instance of KeyValuePairOfStrings with given key and value.
     * @param newKey is new key.
     * @param newValue is new value.
     */
    public KeyValuePairOfStrings(String newKey, String newValue) {
        key = newKey;
        value = newValue;
    }
}