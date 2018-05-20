package ru.spbau.erokhina.xunit_test.testingClasses;

import ru.spbau.erokhina.xunit.annotations.Test;

public class SimpleClass {
    @Test
    public void simpleTest() {}

    @Test
    public void unexpectedExceptionTest() {
        throw new NullPointerException();
    }

    public void justMethod() {}

    @Test
    private void justPrivateMethod() {}

    @Test(expected = NullPointerException.class)
    public void expectedExceptionTest() {
        throw new NullPointerException();
    }

    @Test(expected = NullPointerException.class)
    public void noExceptionTest() {}

    @Test(ignore = "not finished yet")
    public void ignoredTest() {}

    @Test(ignore = "not finished yet", expected = Exception.class)
    public static void staticTest() {}
}
