package ru.spbau.erokhina.xunit_test.testingClasses;

import ru.spbau.erokhina.xunit.annotations.AfterClass;
import ru.spbau.erokhina.xunit.annotations.BeforeClass;
import ru.spbau.erokhina.xunit.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class BeforeAfterClassTestingClass {
    @BeforeClass
    public static void beforeClass() {
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

    @AfterClass
    public static void afterClass() {
        list.add("after");
    }

    public static List<String> list = new ArrayList<>();
}
