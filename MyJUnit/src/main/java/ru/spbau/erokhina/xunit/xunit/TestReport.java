package ru.spbau.erokhina.xunit.xunit;

/**
 * Information about the result of the test.
 */
public class TestReport {
    private String className;
    private String testName;
    private boolean isSuccessful;
    private long time;
    private String reasonOfIgnoring;
    private boolean isExpectedExceptionWasThrown;
    private Exception thrownException;

    /**
     * Constructor for ignored test.
     * @param testClass name of class
     * @param testName test name
     * @param isSuccessful if it run successfully
     * @param time execution time
     * @param reason reason of ignoring
     */
    TestReport(String testClass, String testName, boolean isSuccessful, long time, String reason) {
        this.className = testClass;
        this.testName = testName;
        this.isSuccessful = isSuccessful;
        this.time = time;
        this.reasonOfIgnoring = reason;
    }

    /**
     * Constructor for other tests.
     * @param testClass name of class
     * @param testName test name
     * @param isSuccessful if it run successfully
     * @param time execution time
     * @param isExpectedExceptionWasThrown if expected exception was thrown
     * @param thrownException actual exception that was thrown in the test
     */
    TestReport(String testClass, String testName, boolean isSuccessful, long time,
                      boolean isExpectedExceptionWasThrown, Exception thrownException) {
        this.className = testClass;
        this.testName = testName;
        this.isSuccessful = isSuccessful;
        this.time = time;
        this.isExpectedExceptionWasThrown = isExpectedExceptionWasThrown;
        this.thrownException = thrownException;
    }

    /**
     * Returns class name.
     * @return class name
     */
    public String getClassName() {
        return className;
    }

    /**
     * Returns test name.
     * @return test name
     */
    public String getTestName() {
        return testName;
    }

    /**
     * Returns executing time.
     * @return executing time
     */
    public long getTime() {
        return time;
    }

    /**
     * Returns true if the test was successfully run.
     * @return true if the test was successfully run
     */
    public boolean isSuccessful() {
        return isSuccessful;
    }

    /**
     * Returns actual exception that was thrown.
     * @return actual exception that was thrown
     */
    public Exception getThrownException() {
        return thrownException;
    }

    /**
     * Returns the reason of ignoring.
     * @return the reason of ignoring
     */
    public String getReasonOfIgnoring() {
        return reasonOfIgnoring;
    }

    /**
     * Returns true if expected exception was thrown.
     * @return true if expected exception was thrown
     */
    public boolean isExpectedExceptionWasThrown() {
        return isExpectedExceptionWasThrown;
    }
}
