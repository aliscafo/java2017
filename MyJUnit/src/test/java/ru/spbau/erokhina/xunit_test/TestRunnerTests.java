package ru.spbau.erokhina.xunit_test;

import org.junit.Test;
import ru.spbau.erokhina.xunit.xunit.TestReport;
import ru.spbau.erokhina.xunit.xunit.TestRunner;
import ru.spbau.erokhina.xunit_test.testingClasses.BeforeAfterClassTestingClass;
import ru.spbau.erokhina.xunit_test.testingClasses.BeforeAfterTestingClass;
import ru.spbau.erokhina.xunit_test.testingClasses.SimpleClass;

import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestRunnerTests {
    @Test
    public void simpleTest() throws Exception {
        TestRunner testRunner = new TestRunner(SimpleClass.class);
        List<TestReport> reports = testRunner.runTests();
        assertTrue(reports.size() == 5);

        reports.sort(Comparator.comparing(TestReport::getTestName));

        TestReport report0 = reports.get(0);
        assertTrue(report0.getClassName().equals(SimpleClass.class.getName()));
        assertTrue(report0.getTestName().equals("expectedExceptionTest"));
        assertTrue(report0.isSuccessful());
        assertTrue(report0.getThrownException().getClass().getSimpleName().equals("NullPointerException"));

        TestReport report1 = reports.get(1);
        assertTrue(report1.getClassName().equals(SimpleClass.class.getName()));
        assertTrue(report1.getTestName().equals("ignoredTest"));
        assertTrue(report1.getReasonOfIgnoring().equals("not finished yet"));

        TestReport report2 = reports.get(2);
        assertTrue(report2.getClassName().equals(SimpleClass.class.getName()));
        assertTrue(report2.getTestName().equals("noExceptionTest"));
        assertTrue(!report2.isSuccessful());
        assertTrue(report2.getThrownException() == null);

        TestReport report3 = reports.get(3);
        assertTrue(report3.getClassName().equals(SimpleClass.class.getName()));
        assertTrue(report3.getTestName().equals("simpleTest"));
        assertTrue(report3.isSuccessful());

        TestReport report4 = reports.get(4);
        assertTrue(report4.getClassName().equals(SimpleClass.class.getName()));
        assertTrue(report4.getTestName().equals("unexpectedExceptionTest"));
        assertTrue(!report4.isSuccessful());
        assertTrue(report4.getThrownException().getClass().getSimpleName().equals("NullPointerException"));
    }

    @Test
    public void beforeAfterTest() throws Exception {
        final TestRunner testRunner = new TestRunner(BeforeAfterTestingClass.class);
        testRunner.runTests();
        assertTrue(BeforeAfterTestingClass.list.size() == 6);

        assertTrue(BeforeAfterTestingClass.list.get(0).equals("before"));
        assertTrue(BeforeAfterTestingClass.list.get(1).startsWith("test"));
        assertTrue(BeforeAfterTestingClass.list.get(2).equals("after"));

        assertTrue(BeforeAfterTestingClass.list.get(3).equals("before"));
        assertTrue(BeforeAfterTestingClass.list.get(4).startsWith("test"));
        assertTrue(BeforeAfterTestingClass.list.get(5).equals("after"));
    }

    @Test
    public void beforeAfterClassTest() throws Exception {
        final TestRunner testRunner = new TestRunner(BeforeAfterClassTestingClass.class);
        testRunner.runTests();

        assertTrue(BeforeAfterClassTestingClass.list.size() == 4);

        assertTrue(BeforeAfterClassTestingClass.list.get(0).equals("before"));
        assertTrue(BeforeAfterClassTestingClass.list.get(1).startsWith("test"));
        assertTrue(BeforeAfterClassTestingClass.list.get(2).startsWith("test"));
        assertTrue(BeforeAfterClassTestingClass.list.get(3).equals("after"));
    }
}
