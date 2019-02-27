package ru.spbau.erokhina.cw2;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Class for creating an instance of given type, taking into account given dependencies.
 */
public class Injector {
    static private Set<Class<?>> availableImpls;
    static private HashSet<Class<?>> needInstance;
    static private HashMap<Class<?>, Object> toInstance;

    static {
        availableImpls = new HashSet<>();
        needInstance = new HashSet<>();
        toInstance = new HashMap<>();
    }

    static void clearAll() {
        availableImpls.clear();
        needInstance.clear();
        toInstance.clear();
    }

    /**
     * Creates an instance of given class.
     * @param rootClassName name of class.
     * @param dependenciesImplementationsStr the list of the dependencies.
     * @return an instance of given class.
     * @throws InjectionCycleException if there is a cycle among the dependencies.
     * @throws AmbiguousImplementationException if there are several (> 1) implementation classes.
     * @throws ImplementationNotFoundException if there is not any possible implementation.
     */
    public static Object initialize (String rootClassName, List<String> dependenciesImplementationsStr)
            throws ClassNotFoundException, InjectionCycleException, NoSuchMethodException,
            IllegalAccessException, InstantiationException, InvocationTargetException,
            AmbiguousImplementationException, ImplementationNotFoundException {

        clearAll();

        Class <?> rootClass = Class.forName(rootClassName);
        availableImpls.add(rootClass);

        for (String impl : dependenciesImplementationsStr)
            availableImpls.add(Class.forName(impl));

        return createInstanceOfClass(rootClass);
    }

    private static Object createInstanceOfClass (Class <?> givenClass)
            throws InjectionCycleException, NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException,
            AmbiguousImplementationException, ImplementationNotFoundException {
        if (toInstance.containsKey(givenClass)) {
            return toInstance.get(givenClass);
        }

        if (needInstance.contains(givenClass)) {
            throw new InjectionCycleException();
        }

        needInstance.add(givenClass);

        Class<?> dependencyTypes[] = givenClass.getDeclaredConstructors()[0].getParameterTypes();
        ArrayList<Object> dependencyInstances = getParameters(dependencyTypes);

        Object newInstance = givenClass.getDeclaredConstructors()[0].newInstance(dependencyInstances.toArray());
        toInstance.put(givenClass, newInstance);

        needInstance.remove(givenClass);

        return newInstance;
    }

    private static ArrayList<Object> getParameters(Class<?>[] types)
            throws AmbiguousImplementationException, ImplementationNotFoundException,
            IllegalAccessException, InstantiationException {
        ArrayList<Object> dependencyInstances = new ArrayList<>();

        for (Class<?> curType : types) {
            Class <?> impl = null;

            for (Class <?> curImpl : availableImpls) {
                if (curType.isAssignableFrom(curImpl)) {
                    if (impl != null)
                        throw new AmbiguousImplementationException();
                    impl = curImpl;
                }
            }

            if (impl == null)
                throw new ImplementationNotFoundException();
            dependencyInstances.add(impl.newInstance());
        }

        return dependencyInstances;
    }
}
