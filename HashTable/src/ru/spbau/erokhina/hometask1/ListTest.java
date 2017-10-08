package ru.spbau.erokhina.hometask1;

import org.junit.Test;

import static org.junit.Assert.*;

public class ListTest {
    @Test
    public void getPair() throws Exception {
        List list = new List();
        list.put("aba", "caba");

        KeyValuePairOfStrings keyValue = new KeyValuePairOfStrings("aba", "caba");

        assertNull(list.getPair());

        assertTrue(keyValue.key().equals(list.getNext().getPair().key()));
        assertTrue(keyValue.value().equals(list.getNext().getPair().value()));
    }

    @Test
    public void find() throws Exception {
        List list = new List();
        list.put("aba", "caba");
        list.put("bbb", "yellow");
        list.put("aba", "red");

        assertTrue(list.find("aba").value().equals("red"));
        assertTrue(list.find("bbb").value().equals("yellow"));
    }

    @Test
    public void put() throws Exception {
        List list = new List();
        assertNull(list.put("aba", "caba"));
        assertNull(list.put("bbb", "yellow"));
        assertEquals("caba", list.put("aba", "red"));
    }

    @Test
    public void remove() throws Exception {
        List list = new List();
        list.put("aba", "caba");
        list.put("bbb", "yellow");
        list.put("aba", "red");

        assertNull(list.remove("ooo"));
        assertEquals("red", list.remove("aba"));
        assertEquals("yellow", list.remove("bbb"));
    }
}