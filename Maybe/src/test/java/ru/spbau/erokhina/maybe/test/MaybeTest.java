package ru.spbau.erokhina.maybe.test;

import org.junit.Test;
import ru.spbau.erokhina.maybe.src.ExceptionMaybe;
import ru.spbau.erokhina.maybe.src.Maybe;

import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.*;

/**
 * Class for testing Maybe class.
 */
public class MaybeTest {
    /**
     * Test for checking correctness of just method.
     */
    @Test
    public void just() throws Exception {
        Maybe<Integer> newMaybe = Maybe.just(10);
        assertEquals(newMaybe.get(), (Integer)10);
    }

    /**
     * Test for checking correctness of nothing method.
     */
    @Test(expected = ExceptionMaybe.class)
    public void nothing() throws Exception {
        Maybe<Integer> newMaybe = Maybe.nothing();
        newMaybe.get();
    }

    /**
     * Test for checking correctness of get method for meaningful value.
     */
    @Test
    public void getJust() throws Exception {
        Maybe<Integer> newMaybeInt = Maybe.just(1000);
        assertTrue(newMaybeInt.get().equals(1000));

        Maybe<String> newMaybeStr = Maybe.just("abacaba");
        assertTrue(newMaybeStr.get().equals("abacaba"));

        Maybe<Boolean> newMaybeBool = Maybe.just(true);
        assertTrue(newMaybeBool.get());
    }

    /**
     * Test for checking correctness of get method for empty value.
     */
    @Test (expected = ExceptionMaybe.class)
    public void getNothing() throws Exception {
        Maybe<String> newMaybe = Maybe.nothing();
        newMaybe.get();
    }

    /**
     * Test for checking correctness of isPresent method for meaningful value.
     */
    @Test
    public void isPresentTrue() throws Exception {
        Maybe<Integer> newMaybeInt = Maybe.just(1000);
        assertTrue(newMaybeInt.isPresent());

        Maybe<String> newMaybeStr = Maybe.just("abacaba");
        assertTrue(newMaybeStr.isPresent());

        Maybe<Boolean> newMaybeBool = Maybe.just(true);
        assertTrue(newMaybeBool.isPresent());
    }

    /**
     * Test for checking correctness of isPresent method for empty value.
     */
    @Test
    public void isPresentFalse() throws Exception {
        Maybe<Integer> newMaybeInt = Maybe.nothing();
        assertFalse(newMaybeInt.isPresent());

        Maybe<String> newMaybeStr = Maybe.nothing();
        assertFalse(newMaybeStr.isPresent());

        Maybe<Boolean> newMaybeBool = Maybe.nothing();
        assertFalse(newMaybeBool.isPresent());
    }

    /**
     * Test for checking correctness of map method for meaningful integer value.
     */
    @Test
    public void mapJustInt() throws Exception {
        Maybe<Integer> newMaybeInt = Maybe.just(10);
        assertTrue(newMaybeInt.map(x -> x * 40).get().equals(400));
    }

    /**
     * Test for checking correctness of map method for meaningful string value.
     */
    @Test
    public void mapJustStrLen() throws Exception {
        Maybe<String> newMaybeStr = Maybe.just("abacaba");
        assertTrue(newMaybeStr.map(x -> x.length()).get().equals(7));
    }

    /**
     * Test for checking correctness of map method for empty value.
     */
    @Test (expected = ExceptionMaybe.class)
    public void mapNothing() throws Exception {
        Maybe<Integer> newMaybeInt = Maybe.nothing();
        newMaybeInt.map(x -> x*40).get();
    }

    /**
     * Writes given value using given writer or "null" if value is null.
     * @param writer given writer.
     * @param num given number.
     * @throws IOException
     */
    private void writeNumOrNull(Writer writer, Integer num) throws IOException {
        if (num == null) {
            writer.write("null\n");
            return;
        }
        Integer numSqr = new Integer(num * num);
        writer.write(numSqr.toString() + "\n");
    }

    /**
     * Test for checking correctness of read and write methods.
     */
    @Test
    public void readAndWriteCheck() throws Exception {
        String sep = File.separator;
        String fileName = System.getProperty("user.dir") + File.separator + "src"
                + sep + "testData" + sep + "inputFile.txt";

        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        String fileNameOut = System.getProperty("user.dir") + File.separator + "src"
                + sep + "testData" + sep + "outputFile.txt";

        FileWriter fileWriter = new FileWriter(fileNameOut);
        Writer writer = new BufferedWriter(fileWriter);

        Boolean[] expectedPres = {true, false, true, false, true};
        Integer[] expectedNums = {789, null, 5678765, null, 1};

        for (int i = 0; i < 5; i++) {
            Maybe<Integer> maybe = Maybe.readNum(scanner);
            boolean isPres = maybe.isPresent();
            assertEquals(expectedPres[i], isPres);

            if (!isPres)
                writeNumOrNull(writer, null);
            else {
                assertTrue(maybe.get().equals(expectedNums[i]));
                writeNumOrNull(writer, maybe.get());
            }
        }
        writer.close();

        File fileOut = new File(fileNameOut);
        Scanner scannerOut = new Scanner(fileOut);

        String[] expectedLines = {"622521", "null", "1757466857", "null", "1"};

        for (int i = 0; i < 5; i++) {
            String line = scannerOut.nextLine();
            assertTrue(line.equals(expectedLines[i]));
        }

    }
}