package ru.spbau.erokhina.hw12;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Class with two methods:
 * printStructure creates file with description of given class;
 * diffClasses prints all fields and methods of two given classes that are different.
 */
public class Reflector {
    /**
     * Creates file with description of given class.
     * @param someClass diven class
     */
    public static void printStructure(Class<?> someClass) throws IOException {
        String sep = File.separator;
        String directoryName = System.getProperty("user.dir") + sep + "src"
                + sep + "main" + sep + "java" + sep + "testData";
        String fileNameOut = directoryName + sep + someClass.getSimpleName() + ".java";

        File directory = new File(directoryName);
        directory.mkdir();

        File file = new File(fileNameOut);
        file.createNewFile();

        FileWriter fileWriter = new FileWriter(fileNameOut);
        Writer writer = new BufferedWriter(fileWriter);
        //Writer writer = new OutputStreamWriter(System.out);

        writer.append("package testData;\n\n");
        recPrintStructure(someClass, writer, 0);

        writer.close();
        fileWriter.close();
    }

    /**
     * Prints to the writer all fields and methods of two given classes that are different.
     * @param a first given class
     * @param b second given class
     * @param writer given writer
     */
    public static void diffClasses(Class<?> a, Class<?> b, Writer writer) throws IOException {
        Field[] fieldsA = a.getDeclaredFields();
        Field[] fieldsB = b.getDeclaredFields();

        Set<Field> fieldsSetA = new HashSet<Field>(Arrays.asList(fieldsA));
        Set<Field> fieldsSetB = new HashSet<Field>(Arrays.asList(fieldsB));

        writer.append("\nFields of " + a.getSimpleName() + " and not of " + b.getSimpleName() + ":\n");
        for (Field field : fieldsA) {
            if (!fieldsSetB.contains(field)) {
                appendField(writer, field);
            }
        }

        writer.append("\nFields of " + b.getSimpleName() + " and not of " + a.getSimpleName() + ":\n");
        for (Field field : fieldsB) {
            if (!fieldsSetA.contains(field)) {
                appendField(writer, field);
            }
        }

        Method[] methodsA = a.getDeclaredMethods();
        Method[] methodsB = b.getDeclaredMethods();

        Set<Method> methodsSetA = new HashSet<Method>(Arrays.asList(methodsA));
        Set<Method> methodsSetB = new HashSet<Method>(Arrays.asList(methodsB));

        writer.append("\nMethods of " + a.getSimpleName() + " and not of " + b.getSimpleName() + ":\n");
        for (Method method : methodsA) {
            if (!methodsSetB.contains(method)) {
                appendMethod(writer, method);
            }
        }

        writer.append("\nMethods of " + b.getSimpleName() + " and not of " + a.getSimpleName() + ":\n");
        for (Method method : methodsB) {
            if (!methodsSetA.contains(method)) {
                appendMethod(writer, method);
            }
        }
    }

    public static void recPrintStructure(Class<?> someClass, Writer writer, int level) throws IOException {
        printHeader(someClass, writer, level);
        printBody(someClass, writer, level);
    }

    private static void printHeader(Class<?> someClass, Writer writer, int level) throws IOException {
        printMultipleTabs(writer, level);
        writer.append(getModifiers(someClass.getModifiers()));
        if (getModifiers(someClass.getModifiers()) != "")
            writer.append(" ");

        if (!someClass.isInterface()) {
            writer.append("class ");
        }

        writer.append(someClass.getSimpleName());

        writer.append(getGenericType(someClass.toGenericString()) + " ");

        if (someClass.getSuperclass() != null && !someClass.getSuperclass().getSimpleName().equals("Object")) {
            writer.append("extends " + someClass.getSuperclass().getSimpleName());
            writer.append(getGenericType(someClass.getGenericSuperclass().getTypeName()));
            writer.append(" ");
        }

        printInterfaces(someClass, writer);
    }

    private static void printInterfaces(Class<?> someClass, Writer writer) throws IOException {
        Class[] interfaces = someClass.getInterfaces();
        Type[] genericInterfaces = someClass.getGenericInterfaces();
        for (int i = 0, size = interfaces.length; i < size; i++) {
            writer.append(i == 0 ? "implements " : ", ");
            writer.append(interfaces[i].getSimpleName());
            writer.append(getGenericType(genericInterfaces[i].getTypeName()));
        }
        if (interfaces.length > 0)
            writer.append(" ");
    }

    private static String getGenericType(String toGeneric) throws IOException {
        int indexLeft = toGeneric.indexOf('<');
        int indexRight = toGeneric.indexOf('>');
        if (indexLeft == -1)
            return "";

        String[] types = toGeneric.substring(indexLeft + 1, indexRight).split(", ");

        for (int i = 0; i < types.length; i++) {
            String[] res = types[i].split("\\.");
            types[i] = res[res.length - 1];
        }

        return "<" + String.join(", ", types) + ">";
    }

    private static void printBody(Class<?> someClass, Writer writer, int level) throws IOException {
        writer.append("{\n");
        printFields(someClass, writer, level);
        printConstructors(someClass, writer, level);
        printMethods(someClass, writer, level);
        printInnerClasses(someClass, writer, level);
        writer.append("\n");
        printMultipleTabs(writer, level);
        writer.append("}");
    }

    private static void printInnerClasses(Class<?> someClass, Writer writer, int level) throws IOException {
        Class[] classes = someClass.getDeclaredClasses();
        for (Class cl : classes) {
            recPrintStructure(cl, writer, level + 1);
        }
    }

    public static void printFields(Class<?> someClass, Writer writer, int level) throws IOException {
        Field[] fields = someClass.getDeclaredFields();
        for (Field field : fields) {
            printMultipleTabs(writer, level + 1);
            writer.append(getModifiers(field.getModifiers())
                    + " " + field.getGenericType().toString() + " " + field.getName() + ";\n");
        }
    }

    public static void printConstructors(Class<?> someClass, Writer writer, int level) throws IOException {
        Constructor[] constructors = someClass.getDeclaredConstructors();
        for (Constructor constructor : constructors) {
            String modifiers = getModifiers(constructor.getModifiers());
            printMultipleTabs(writer, level + 1);
            writer.append(modifiers);
            if (!modifiers.isEmpty())
                writer.append(" ");
            writer.append(someClass.getSimpleName() + "(");
            writer.append(getArgumentsTypes(constructor.getGenericParameterTypes()));
            writer.append(") {}\n");
        }
    }

    public static void printMethods(Class<?> someClass, Writer writer, int level) throws IOException {
        Method[] methods = someClass.getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            printMultipleTabs(writer, level + 1);
            for (Annotation annotation : annotations)
                writer.append("@" + annotation.annotationType().getSimpleName() + " ");
            writer.append("\n");

            printMultipleTabs(writer, level + 1);
            writer.append(getModifiers(method.getModifiers()) + " " + method.getGenericReturnType()
                    + " " + method.getName() + "(");
            writer.append(getArgumentsTypes(method.getGenericParameterTypes()));
            writer.append(") {\n");
            if (!method.getGenericReturnType().toString().equals("void")) {
                printMultipleTabs(writer, level + 2);
                if (method.getReturnType().isPrimitive()) {
                    writer.append("return 0;\n");
                }
                else {
                    writer.append("return null;\n");
                }
            }
            printMultipleTabs(writer, level + 1);
            writer.append("}\n");
        }
    }

    private static void printMultipleTabs(Writer writer, int k) throws IOException {
        for (int i = 0; i < k; i++) {
            writer.append("\t");
        }
    }

    private static String getArgumentsTypes(Type[] params) {
        String args = "";
        for (int i = 0, size = params.length; i < size; i++) {
            if (i > 0) args += ", ";
            args += longTypeToShort(params[i].getTypeName()) + " arg" + i;
        }
        return args;
    }

    private static String longTypeToShort(String type) {
        String[] arr = type.split("\\.");
        return arr[arr.length - 1];
    }

    private static String getModifiers(int modifiers) {
        return Modifier.toString(modifiers);
    }

    private static void appendField(Writer writer, Field field) throws IOException {
        writer.append(getModifiers(field.getModifiers())
                + " " + field.getGenericType().toString() + " " + field.getName() + ";\n");
    }

    private static void appendMethod(Writer writer, Method method) throws IOException {
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations)
            writer.append("@" + annotation.annotationType().getSimpleName() + " ");
        if (annotations.length > 0)
            writer.append("\n");

        writer.append(getModifiers(method.getModifiers()) + " " + method.getGenericReturnType()
                + " " + method.getName() + "(");
        writer.append(getArgumentsTypes(method.getGenericParameterTypes()));
        writer.append(");\n");
    }
}
