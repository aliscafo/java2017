package ru.spbau.erokhina.hw_lazy;

import org.junit.Test;

import java.util.function.Supplier;

import static org.junit.Assert.*;

/**
 * Class for testing LazyFactory.
 */
public class LazyFactoryTest {
    private int countOfCalls = 0;
    private volatile int countOfCallsVolatile = 0;

    @Test
    public void testCreateSimpleLazy() throws Exception {
        Lazy<Integer> lazy = LazyFactory.createSimpleLazy(() -> 700);
        assertTrue(lazy.get().equals(700));
    }

    @Test
    public void testGetSimpleLazy() throws Exception {
        Lazy<Integer> lazy = LazyFactory.createSimpleLazy(() -> { countOfCalls++; return 700; });

        lazy.get();
        lazy.get();

        assertEquals(1, countOfCalls);
    }

    @Test
    public void testLazinessSimpleLazy() throws Exception {
        Lazy<Integer> lazy = LazyFactory.createSimpleLazy(() -> { countOfCalls++; return 700; });

        assertEquals(0, countOfCalls);

        lazy.get();

        assertEquals(1, countOfCalls);
    }

    @Test
    public void testMultiThreadedSimpleLazy() throws Exception {
        Lazy<Integer> lazy = LazyFactory.createMultiThreadedLazy(() -> { countOfCallsVolatile++; return 700; });

        Thread thread1 = new Thread(() -> lazy.get());
        Thread thread2 = new Thread(() -> lazy.get());

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertEquals(1, countOfCallsVolatile);
    }

    @Test
    public void testMultiThreadedSimpleLazyComplex() throws Exception {
        int[] marks = new int[10];
        Lazy[] lazies = new Lazy[10];

        for (int i = 0; i < 10; i++) {
            marks[i] = 0;
            final int index = i;
            lazies[i] = LazyFactory.createMultiThreadedLazy(() -> { marks[index]++; return 700; });
        }

        Thread[] threads = new Thread[20];
        for (int i = 0; i < 20; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 10; j++) {
                    lazies[j].get();
                }
            });
        }

        for (int i = 0; i < 20; i++) {
            threads[i].start();
        }

        for (int i = 0; i < 20; i++) {
            threads[i].join();
        }

        for (int i = 0; i < 10; i++) {
            assertEquals(1, marks[i]);
        }
    }
}