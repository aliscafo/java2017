package ru.spbau.erokhina.hw12;

import java.util.TreeSet;

public abstract class SimpleClass<T, V> {
    private T[] firstField;
    private V secondField;
    public SimpleClass(T arg, String t) {}
    public SimpleClass(V v, Integer t) {}
    public void simpleMethod() {}
    private T returnGeneric(V a, int b) { return null; }
}

interface SimpleInterface {
}

interface GenericInterface<U> {

}

class MyClass implements GenericInterface<Integer> {
    private int firstField;
    private int secondField;
    public void simpleVoidMethod() {}
}

class MyClassA {}

class MyClassB extends MyClassA {
    private int firstField;
    private int secondField;
    public void simpleMethod() {}
}

class ComplexClass<T, V> implements SimpleInterface, GenericInterface<Character> {
    public ComplexClass(Object arg, String t) {
    }
}

