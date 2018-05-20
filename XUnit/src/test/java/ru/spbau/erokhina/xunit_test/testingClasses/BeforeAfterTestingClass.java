package ru.spbau.erokhina.xunit_test.testingClasses;

import ru.spbau.erokhina.xunit.annotations.After;
import ru.spbau.erokhina.xunit.annotations.Before;
import ru.spbau.erokhina.xunit.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class BeforeAfterTestingClass {
    @Before
    public void before() {
        list.add("before");
    }

    @Test
    public void test1() {
        list.add("test1");
    }

    @Test
    public void test2() {
        list.add("test2");
    }

    @After
    public void after() {
        list.add("after");
    }

    public static List<String> list = new ArrayList<>();
}
