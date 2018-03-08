package ru.spbau.erokhina.threadpool;

import org.junit.Test;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.junit.Assert.*;

/**
 * Class for ThreadPoolImpl testing.
 */
public class ThreadPoolImplTest {
    private final ArrayList<Integer> list = new ArrayList<>();

    @Test
    public void oneThreadTest() throws InterruptedException, LightExecutionException {
        ThreadPool pool = new ThreadPoolImpl(1);
        LightFuture<Boolean> task = pool.add(() -> true);

        Thread.sleep(20);

        assertTrue(task.isReady());
    }

    @Test
    public void oneTaskTest() throws InterruptedException, LightExecutionException {
        ThreadPool threadPool = new ThreadPoolImpl(7);
        LightFuture<Boolean> task = threadPool.add(() -> true);

        assertTrue(task.get());
    }

    @Test(timeout=2000)
    public void simpleTest() throws Exception {
        ThreadPool threadPool = new ThreadPoolImpl(5);
        for (int i = 0; i < 50; i++) {
            threadPool.add(() ->
            {
                synchronized(list) {
                list.add(1);
                };
                return 8;
            });
        }

        while (list.size() != 50) {}
        threadPool.shutdown();
    }

    @Test(timeout=2000)
    public void thenApplySimple() throws InterruptedException, LightExecutionException {
        ThreadPool threadPool = new ThreadPoolImpl(5);
        LightFuture<Integer> future = threadPool.add(() -> 40);

        while (!future.isReady()) {}

        assertEquals(40, future.get().intValue());
        LightFuture<Integer> future2 = future.thenApply(x -> x + 9);

        while (!future2.isReady()) {}
        assertEquals(49, future2.get().intValue());
    }

    @Test
    public void shutdownTest() throws InterruptedException, LightExecutionException {
        ThreadPool threadPool = new ThreadPoolImpl(5);
        ArrayList<LightFuture> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(threadPool.add(() -> {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException ignored) {
                }
                return true;}));
        }

        threadPool.shutdown();

        boolean flag = false;
        for (LightFuture lightFuture : list) {
            if (!lightFuture.isReady()) {
                flag = true;
            }
        }

        assertTrue(flag);
    }

    @Test
    public void thenApplyInnerTasksTest() throws Exception {
        ThreadPool threadPool = new ThreadPoolImpl(7);
        LightFuture<Integer> task0 = threadPool.add(() -> 100);

        LightFuture<Integer> task1 = task0.thenApply(o -> o + 1);
        LightFuture<Integer> task2 = task1.thenApply(o -> o + 1);
        LightFuture<Integer> task3 = task2.thenApply(o -> o + 1);

        while (!task3.isReady()) {}

        assertEquals(103, (int) task3.get());
        threadPool.shutdown();
    }


}