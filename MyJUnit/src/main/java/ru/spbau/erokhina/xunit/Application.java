package ru.spbau.erokhina.xunit;

import ru.spbau.erokhina.xunit.exceptions.AfterClassException;
import ru.spbau.erokhina.xunit.exceptions.BeforeClassException;
import ru.spbau.erokhina.xunit.exceptions.TestException;
import ru.spbau.erokhina.xunit.xunit.TestReport;
import ru.spbau.erokhina.xunit.xunit.TestRunner;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Command-line application for testing classes located in given path.
 */
public class Application {
    /**
     * Runs the application.
     * The first argument is given path.
     * @param args contains given path.
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        if (args.length > 1) {
            System.out.println("Too many arguments.");
            return;
        } else if (args.length == 0) {
            System.out.println("Not enough arguments.");
            return;
        }

        List<Class<?>> classesForTesting;
        classesForTesting = getClassesByPath(Paths.get(args[0]));

        for (Class<?> testClass : classesForTesting) {
            try {
                List<TestReport> reports = new TestRunner(testClass).runTests();

                System.out.println("Test reports from " + testClass.getName() + ":");

                int testNumber = 1;
                int successfulNumber = 0;

                for (TestReport report : reports) {
                    System.out.println();
                    System.out.println("TEST " + testNumber +
                            (report.getReasonOfIgnoring().equals("") ? "" : " (IGNORED)") + ": " +
                            report.getClassName() + "::" + report.getTestName());

                    if (!report.isSuccessful()) {
                        System.out.print("REASON: ");
                        if (!report.isExpectedExceptionWasThrown()) {
                            System.out.println("Unexpected exception was thrown : " +
                                    report.getThrownException().getClass().getSimpleName());
                        }
                    }

                    if (!report.getReasonOfIgnoring().equals("")) {
                        System.out.println("RESULT: " + (report.isSuccessful() ? "OK" : "FAILED"));
                        System.out.println("TIME: " + report.getTime() + " ms");
                        if (report.isSuccessful()) {
                            successfulNumber++;
                        }
                        testNumber++;
                    }
                }

                System.out.println();
                System.out.println(successfulNumber + " of " + reports.size() + " tests are successful.");

            } catch (TestException e) {
                System.out.println("TestException: " + e.getMessage());
            } catch (BeforeClassException e) {
                System.out.println("BeforeClassException: " + e.getMessage());
            } catch (AfterClassException e) {
                System.out.println("AfterClassException: " + e.getMessage());
            }
        }

    }

    private static List<Class<?>> getClassesByPath(Path pathWithClasses) throws IOException, ClassNotFoundException {
        URLClassLoader loader = new URLClassLoader(new URL[]{pathWithClasses.toUri().toURL()});

        List<String> testClassNames = getAllClassNames(pathWithClasses);

        List<Class<?>> classes = new ArrayList<>();

        for (String className: testClassNames) {
            try {
                classes.add(loader.loadClass(className));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return classes;
    }

    private static List<String> getAllClassNames(Path pathWithClasses) throws IOException {
        return Files.walk(pathWithClasses).filter(path -> path.getFileName().toString().endsWith(".class"))
                .map(pathWithClasses::relativize)
                .map(classPath -> classPath.toString().replace(File.separatorChar, '.'))
                .map(fileName -> fileName.substring(0, fileName.lastIndexOf('.')))
                .collect(Collectors.toList());
    }
}
