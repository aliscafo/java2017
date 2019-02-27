package ru.spbau.erokhina.cw2.testClasses;

import org.junit.Test;
import ru.spbau.erokhina.cw2.Injector;

import java.util.Collections;

import static org.junit.Assert.assertTrue;

public class InjectorTest {

    @Test
    public void injectorShouldInitializeClassWithoutDependencies()
            throws Exception {
        Object object = Injector.initialize("ru.spbau.erokhina.cw2.testClasses.ClassWithoutDependencies",
                Collections.emptyList());
        assertTrue(object instanceof ClassWithoutDependencies);
    }

    @Test
    public void injectorShouldInitializeClassWithOneClassDependency()
            throws Exception {
        Object object = Injector.initialize(
                "ru.spbau.erokhina.cw2.testClasses.ClassWithOneClassDependency",
                Collections.singletonList("ru.spbau.erokhina.cw2.testClasses.ClassWithoutDependencies")
        );
        assertTrue(object instanceof ClassWithOneClassDependency);
        ClassWithOneClassDependency instance = (ClassWithOneClassDependency) object;
        assertTrue(instance.dependency != null);
    }

    @Test
    public void injectorShouldInitializeClassWithOneInterfaceDependency()
            throws Exception {
        Object object = Injector.initialize(
                "ru.spbau.erokhina.cw2.testClasses.ClassWithOneInterfaceDependency",
                Collections.singletonList("ru.spbau.erokhina.cw2.testClasses.InterfaceImpl")
        );
        assertTrue(object instanceof ClassWithOneInterfaceDependency);
        ClassWithOneInterfaceDependency instance = (ClassWithOneInterfaceDependency) object;
        assertTrue(instance.dependency instanceof InterfaceImpl);
    }
}