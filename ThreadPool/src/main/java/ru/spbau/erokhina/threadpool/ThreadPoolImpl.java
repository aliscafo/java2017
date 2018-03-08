package ru.spbau.erokhina.threadpool;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Class for achieving concurrency of execution of tasks.
 */
@SuppressWarnings("unchecked")
public class ThreadPoolImpl implements ThreadPool {
    private final Queue<AbstractTask> tasks = new LinkedList<>();
    private ArrayList<Thread> threads = new ArrayList<>();

    /**
     * Constructor for creating ThreadPoolImpl.
     * @param n a number of threads
     */
    ThreadPoolImpl(int n) {
        for(int i = 0; i < n; i++) {
            Thread thread = new Thread(new ThreadRunner());
            threads.add(thread);
            thread.start();
        }
    }

    /**
     * All threads will be stopped.
     */
    @Override
    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

     private interface AbstractTask extends LightFuture {
        void run();
        boolean hasException();
        Exception getException();
     }

    private class Task<T> implements AbstractTask {
        private volatile boolean isReady;
        private volatile T result;
        private volatile LightExecutionException exception;
        private Supplier<T> supplier;

        Task(Supplier<T> supplierTask) {
            supplier = supplierTask;
        }

        @Override
        public boolean hasException() {
            return (exception != null);
        }

        @Override
        public Exception getException() {
            return exception;
        }

        @Override
        public boolean isReady() {
            return isReady;
        }

        @Override
        public Object get() throws LightExecutionException {
            while (!isReady) {
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException ignored) {
                    }
                }
            }

            if (exception != null) {
                throw exception;
            }

            return result;
        }

        @Override
        public LightFuture thenApply(Function function) {
            AbstractTask newTask = new HasParent(this, function);
            synchronized (tasks) {
                tasks.add(newTask);
                tasks.notifyAll();
            }
            return newTask;
        }

        public void run() {
            try {
                result = supplier.get();
            }
            catch (Exception e) {
                exception = new LightExecutionException(e);
            }
            isReady = true;
        }
    }

    private class HasParent<U> implements AbstractTask {
        private volatile boolean isReady;
        private volatile U result;
        private volatile LightExecutionException exception;
        private Function function;
        private AbstractTask parent;

        HasParent(AbstractTask parent, Function function) {
            this.parent = parent;
            this.function = function;
        }

        @Override
        public boolean hasException() {
            return (exception != null);
        }

        @Override
        public Exception getException() {
            return exception;
        }

        @Override
        public boolean isReady() {
            return isReady;
        }

        @Override
        public Object get() throws LightExecutionException {
            while (!isReady) {
                synchronized (this) {
                    try {
                        wait();
                    } catch (InterruptedException ignored) {
                    }
                }
            }

            if (exception != null) {
                throw exception;
            }

            return result;
        }

        @Override
        public LightFuture thenApply(Function function) {
            Task newTask = new Task(() -> function.apply(result));
            synchronized (tasks) {
                tasks.add(newTask);
                tasks.notifyAll();
            }
            return newTask;
        }

        public void run() {
            try {
                while (!parent.isReady()) {
                    synchronized (this) {
                        wait();
                    }
                }
                result = (U) function.apply(parent.get());
                if (parent.hasException()) {
                    throw parent.getException();
                }
            }
            catch (Exception e) {
                exception = new LightExecutionException(e);
            } finally {
                isReady = true;
                synchronized (this) {
                    notify();
                }
            }

        }
    }

    /**
     * Adds a task to the pool.
     * @param supplier supplier that holds a task
     * @param <T> a type of return value
     * @return a new task that was added to the pool
     */
    @Override
    public <T> LightFuture<T> add(Supplier<T> supplier) {
        AbstractTask newTask = new Task<>(supplier);
        synchronized (tasks) {
            tasks.add(newTask);
            tasks.notifyAll();
        }
        return newTask;
    }

    private class ThreadRunner implements Runnable {
        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    AbstractTask task;

                    synchronized (tasks) {
                        while (tasks.isEmpty()) {
                            tasks.wait();
                        }
                        task = tasks.poll();
                    }
                    synchronized (task) {
                        task.run();
                        task.notifyAll();
                    }
                    synchronized (tasks) {
                        tasks.notifyAll();
                    }

                }
            } catch (InterruptedException ignored) {
            }
        }
    }
}
