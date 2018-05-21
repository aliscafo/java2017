package ru.spbau.erokhina.cw1;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

/**
 * Class that provides console application for calculating check-sum of directory/file.
 */
public class MD5Calculator {
    private int BUFFER_SIZE = 1024;

    /**
     * Returns check-sum of given path (one-threaded mode).
     * @param path given path
     * @return check sum
     */
    public byte[] findHashOneThreaded(Path path) throws NoSuchAlgorithmException, IOException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");

        if (!Files.isDirectory(path)) {
            InputStream stream = Files.newInputStream(path);
            DigestInputStream md5Stream = new DigestInputStream(stream, md5);
            byte[] buffer = new byte[BUFFER_SIZE];
            while (md5Stream.read(buffer) != -1) {
            }
            return md5Stream.getMessageDigest().digest();
        }
        else {
            md5.update(path.getFileName().toString().getBytes());
            List<Path> files = Files.list(path).collect(Collectors.toList());

            for (Path filePath : files) {
                md5.update(findHashOneThreaded(filePath));
            }
            return md5.digest();
        }
    }

    /**
     * Returns check-sum of given path (fork-join mode).
     * @param path given path
     * @return check sum
     */
    public byte[] findHashForkJoin(Path path) throws NoSuchAlgorithmException, IOException {
        MD5Task task = new MD5Task(path);
        ForkJoinPool pool = new ForkJoinPool();
        return pool.invoke(task);
    }

    private class MD5Task extends RecursiveTask<byte[]> {
        private Path path;

        MD5Task(Path path) {
            this.path = path;
        }

        @Override
        protected byte[] compute() {
            MessageDigest md5 = null;
            try {
                md5 = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            if (!Files.isDirectory(path)) {
                try {
                    InputStream stream = Files.newInputStream(path);
                    DigestInputStream md5Stream = new DigestInputStream(stream, md5);
                    byte[] buffer = new byte[BUFFER_SIZE];
                    while (md5Stream.read(buffer) != -1) {
                    }

                    return md5Stream.getMessageDigest().digest();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    md5.update(path.getFileName().toString().getBytes());

                    List<MD5Task> tasks = new ArrayList<>();

                    List<Path> files = Files.list(path).collect(Collectors.toList());

                    for (Path filePath : files) {
                        tasks.add(new MD5Task(filePath));
                    }

                    for (MD5Task task : tasks) {
                        task.fork();
                    }
                    for (MD5Task task : tasks) {
                        md5.update(task.join());
                    }
                    return md5.digest();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            return new byte[0];
        }
    }

    /**
     * Calculate the time of one-threaded mode.
     * @param calculator given MD5Calculator
     * @param path given path
     * @return the time of one-threaded mode
     */
    public static long calculateTimeOneThreaded(MD5Calculator calculator, Path path) throws IOException, NoSuchAlgorithmException {
        long startTime = System.currentTimeMillis();
        byte[] checkSum = calculator.findHashOneThreaded(path);
        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }

    /**
     * Calculate the time of fork-join mode.
     * @param calculator given MD5Calculator
     * @param path given path
     * @return the time of fork-join mode
     */
    public static long calculateTimeForkJoin(MD5Calculator calculator, Path path) throws IOException, NoSuchAlgorithmException {
        long startTime = System.currentTimeMillis();
        byte[] checkSum = calculator.findHashForkJoin(path);
        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        MD5Calculator calculator = new MD5Calculator();

        Path path = Paths.get(args[0]);

        System.out.println("Time in one-threaded mode: " + calculateTimeOneThreaded(calculator, path));
        System.out.println("Time in fork-join mode: " + calculateTimeForkJoin(calculator, path));

    }
}
