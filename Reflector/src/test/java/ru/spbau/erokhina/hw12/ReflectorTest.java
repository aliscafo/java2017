package ru.spbau.erokhina.hw12;

import org.junit.Test;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import static org.junit.Assert.*;
import static ru.spbau.erokhina.hw12.Reflector.diffClasses;
import static ru.spbau.erokhina.hw12.Reflector.printStructure;

public class ReflectorTest {
    private Class loadClass(String nameClass) throws ClassNotFoundException, MalformedURLException {
        String sep = File.separator;
        String directoryName = System.getProperty("user.dir") + sep + "src"
                + sep + "main" + sep + "java" + sep + "testData";
        String fileNameOut = directoryName + sep + nameClass + ".java";

        executeCommand("cd " + directoryName);
        executeCommand("javac " + fileNameOut);
        File file = new File(directoryName);
        URL url = file.toURL();
        URL[] urls = new URL[]{url};

        ClassLoader cl = new URLClassLoader(urls);
        Class testClass = cl.loadClass("testData." + nameClass);

        return testClass;
    }

    private void compareTwoClasses(Class expected, Class actual) {
        assertEquals(expected.getConstructors().length, actual.getConstructors().length);

        int expectedModifiers = expected.getModifiers();
        assertEquals(expectedModifiers, actual.getModifiers());

        if (expected.getSuperclass() != null && actual.getSuperclass() != null) {
            assertEquals(expected.getSuperclass().getSimpleName(), actual.getSuperclass().getSimpleName());
        }

        Field[] expectedFields = expected.getDeclaredFields();
        Field[] actualFields = actual.getDeclaredFields();
        assertEquals(expectedFields.length, actualFields.length);
        for (int i = 0; i < expectedFields.length; i++) {
            assertEquals(expectedFields[i].getGenericType().getTypeName(),
                    actualFields[i].getGenericType().getTypeName());
        }

        Constructor[] expectedConstructors = expected.getDeclaredConstructors();
        Constructor[] actualConstructors = actual.getDeclaredConstructors();
        assertEquals(expectedConstructors.length, actualConstructors.length);
        for (int i = 0; i < expectedConstructors.length; i++) {
            assertEquals(expectedConstructors[i].getGenericParameterTypes().length,
                    actualConstructors[i].getGenericParameterTypes().length);
            for (int j = 0; j < expectedConstructors[i].getGenericParameterTypes().length; j++) {
                assertEquals(expectedConstructors[i].getGenericParameterTypes()[i].getTypeName(),
                        actualConstructors[i].getGenericParameterTypes()[i].getTypeName());
            }
        }

        Method[] expectedMethods = expected.getDeclaredMethods();
        Method[] actualMethods = actual.getDeclaredMethods();
        assertEquals(expectedMethods.length, actualMethods.length);
        for (int i = 0; i < expectedMethods.length; i++) {
            assertEquals(expectedMethods[i].getGenericReturnType().getTypeName(),
                    actualMethods[i].getGenericReturnType().getTypeName());
            assertEquals(expectedMethods[i].getGenericParameterTypes().length,
                    actualMethods[i].getGenericParameterTypes().length);
            for (int j = 0; j < expectedMethods[i].getGenericParameterTypes().length; j++) {
                assertEquals(expectedMethods[i].getGenericParameterTypes()[i].getTypeName(),
                        actualMethods[i].getGenericParameterTypes()[i].getTypeName());

            }
        }

        Class[] expectedClasses = expected.getDeclaredClasses();
        Class[] actualClasses = actual.getDeclaredClasses();
        assertEquals(expectedClasses.length, actualClasses.length);
        if (expectedClasses.length != 0) {
            for (int i = 0; i < expectedClasses.length; i++) {
                compareTwoClasses(expectedClasses[i], actualClasses[i]);
            }
        }
    }

    @Test
    public void simpleClassTest() throws IOException, ClassNotFoundException {
        printStructure(SimpleClass.class);

        Class testClass = loadClass("SimpleClass");

        compareTwoClasses(SimpleClass.class, testClass);
    }

    @Test
    public void simpleInterfaceTest() throws IOException, ClassNotFoundException {
        printStructure(SimpleInterface.class);

        Class testClass = loadClass("SimpleInterface");

        compareTwoClasses(SimpleInterface.class, testClass);
    }

    @Test
    public void genericInterfaceTest() throws IOException, ClassNotFoundException {
        printStructure(GenericInterface.class);

        Class testClass = loadClass("GenericInterface");

        compareTwoClasses(GenericInterface.class, testClass);
    }

    @Test
    public void myClassTest() throws IOException, ClassNotFoundException {
        printStructure(MyClass.class);

        Class testClass = loadClass("MyClass");

        compareTwoClasses(MyClass.class, testClass);
    }

    @Test
    public void myClassATest() throws IOException, ClassNotFoundException {
        printStructure(MyClassA.class);

        Class testClass = loadClass("MyClassA");

        compareTwoClasses(MyClassA.class, testClass);
    }

    @Test
    public void myClassBTest() throws IOException, ClassNotFoundException {
        printStructure(MyClassB.class);

        Class testClass = loadClass("MyClassB");

        compareTwoClasses(MyClassB.class, testClass);
    }

    @Test
    public void complexClassTest() throws IOException, ClassNotFoundException {
        printStructure(ComplexClass.class);

        Class testClass = loadClass("ComplexClass");

        compareTwoClasses(ComplexClass.class, testClass);
    }

    @Test
    public void diffClassTest() throws IOException, ClassNotFoundException {
        String str;
        Writer writer = new StringWriter();
        diffClasses(SimpleClass.class, MyClass.class, writer);
        //writer.close();

        diffClasses(SimpleClass.class, SimpleClass.class, writer);

        String text = "\nFields of SimpleClass and not of MyClass:\n" +
                "private T[] firstField;\n" +
                "private V secondField;\n" +
                "\n" +
                "Fields of MyClass and not of SimpleClass:\n" +
                "private int firstField;\n" +
                "private int secondField;\n" +
                "\n" +
                "Methods of SimpleClass and not of MyClass:\n" +
                "public void simpleMethod();\n" +
                "private T returnGeneric(V arg0, int arg1);\n" +
                "\n" +
                "Methods of MyClass and not of SimpleClass:\n" +
                "public void simpleVoidMethod();\n" +
                "\n" +
                "Fields of SimpleClass and not of SimpleClass:\n" +
                "\n" +
                "Fields of SimpleClass and not of SimpleClass:\n" +
                "\n" +
                "Methods of SimpleClass and not of SimpleClass:\n" +
                "\n" +
                "Methods of SimpleClass and not of SimpleClass:\n";
        assertEquals(text, writer.toString());
        writer.close();
    }

    private static String executeCommand(String command) {
        StringBuffer output = new StringBuffer();

        Process p;
        try {
            p = Runtime.getRuntime().exec(command);
            p.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return output.toString();
    }
}