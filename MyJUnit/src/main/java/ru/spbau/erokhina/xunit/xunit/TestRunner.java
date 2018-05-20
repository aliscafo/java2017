package ru.spbau.erokhina.xunit.xunit;

import ru.spbau.erokhina.xunit.annotations.*;
import ru.spbau.erokhina.xunit.exceptions.AfterClassException;
import ru.spbau.erokhina.xunit.exceptions.BeforeClassException;
import ru.spbau.erokhina.xunit.exceptions.NotException;
import ru.spbau.erokhina.xunit.exceptions.TestException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Class for running test marked with annotations according to the annotation rules.
 */
public class TestRunner {
    private Class<?> testClass;
    private List<Method> testMethods = new ArrayList<>();
    private List<Method> beforeMethods = new ArrayList<>();
    private List<Method> afterMethods = new ArrayList<>();
    private List<Method> beforeClassMethods = new ArrayList<>();
    private List<Method> afterClassMethods = new ArrayList<>();

    /**
     * Constructor for creating TestRunner for given class.
     * @param givenClass class from which we want to run tests
     */
    public TestRunner(Class<?> givenClass) {
        this.testClass = givenClass;

        List<List<Method>> listOfMethods = new ArrayList<>();
        listOfMethods.add(testMethods);
        listOfMethods.add(beforeMethods);
        listOfMethods.add(afterMethods);
        listOfMethods.add(beforeClassMethods);
        listOfMethods.add(afterClassMethods);

        List<Class<?>> correspondingClasses = new ArrayList<>();
        correspondingClasses.add(Test.class);
        correspondingClasses.add(Before.class);
        correspondingClasses.add(After.class);
        correspondingClasses.add(BeforeClass.class);
        correspondingClasses.add(AfterClass.class);

        for (int i = 0; i < listOfMethods.size(); i++) {
            Class curClass = correspondingClasses.get(i);

            for (Method method : testClass.getMethods()) {
                if (Modifier.isPublic(method.getModifiers()) && method.getParameterCount() == 0 &&
                        ((!curClass.getSimpleName().contains("Class") && !Modifier.isStatic(method.getModifiers())) ||
                        (curClass.getSimpleName().contains("Class") && Modifier.isStatic(method.getModifiers()))) &&
                                method.getReturnType().equals(Void.TYPE) && method.getAnnotation(curClass) != null) {
                    listOfMethods.get(i).add(method);
                }
            }
        }
    }

    /**
     * Method for running tests.
     * @return list of reports
     */
    public List<TestReport> runTests() throws BeforeClassException, AfterClassException, TestException {
        List<TestReport> reports = new ArrayList<>();

        try {
            invokeAllFromList(null, beforeClassMethods);
        } catch (Exception e) {
            throw new BeforeClassException(e);
        }

        for (Method method : testMethods) {
            reports.add(runTestMethod(method));
        }

        try {
            invokeAllFromList(null, afterClassMethods);
        } catch (Exception e) {
            throw new AfterClassException(e);
        }

        return reports;
    }

    private TestReport runTestMethod(Method method) throws TestException {
        Test testAnnotation = method.getAnnotation(Test.class);

        if (!testAnnotation.ignore().equals("")) {
            return new TestReport(testClass.getName(), method.getName(),
                    true, 0, testAnnotation.ignore());
        }

        Exception exception = null;
        Object instance;

        try {
            instance = testClass.newInstance();
        } catch (Exception e) {
            throw new TestException(e);
        }

        long startTimer = System.nanoTime();

        try {
            invokeAllFromList(instance, beforeMethods);
            method.invoke(instance);
            invokeAllFromList(instance, afterMethods);
        } catch (IllegalAccessException e) {
            throw new TestException(e);
        } catch (InvocationTargetException e) {
            exception = (Exception) e.getCause();
        }

        long runningTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTimer);

        if (exception == null && !testAnnotation.expected().equals(NotException.class)) {
            return new TestReport(testClass.getName(), method.getName(),
                    false, runningTime, false, null);
        }

        if (exception != null && !testAnnotation.expected().isInstance(exception)) {
            return new TestReport(testClass.getName(), method.getName(), false,
                    runningTime, false, exception);
        }
        return new TestReport(testClass.getName(), method.getName(), true, runningTime,
                true, exception);
    }

    private void invokeAllFromList(Object instance, List<Method> methods) throws TestException {

        for (Method method : methods) {
            try {
                method.invoke(instance);
            } catch (Exception e) {
                e.printStackTrace();
                throw new TestException(e);
            }
        }
    }

}
