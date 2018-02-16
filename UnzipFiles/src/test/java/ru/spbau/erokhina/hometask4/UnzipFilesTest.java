package ru.spbau.erokhina.hometask4;

import org.junit.Test;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.Assert.*;

public class UnzipFilesTest {
    private void newEntry(String name, String text, ZipOutputStream outputStream) throws IOException {
        ZipEntry entry = new ZipEntry(name);

        outputStream.putNextEntry(entry);
        byte[] data = text.getBytes();
        outputStream.write(data, 0, data.length);
    }

    /**
     * Method for testing unzipping files that match with regex.
     */
    @Test
    public void unzipFilesRegexNormalCase() throws Exception {
        File folder = new File(System.getProperty("user.dir") + File.separator + "testData");
        folder.mkdir();

        File newArchive = new File(folder.getAbsolutePath() + File.separator + "archive.zip");
        newArchive.createNewFile();

        try (ZipOutputStream outStream = new ZipOutputStream(new FileOutputStream(newArchive))) {
            newEntry("test1.txt", "Just first text for testing.", outStream);
            newEntry("test2.txt", "Just second text for testing.", outStream);
            newEntry("test3.txt", "Just third text for testing.", outStream);
            newEntry("badTest.txt", "Just bad text for testing.", outStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        UnzipFiles unzip = new UnzipFiles(".zip");
        unzip.UnzipFilesRegex(folder.getAbsolutePath(), "^test.\\.txt$");

        File archiveFolder = new File(
                folder + File.separator + "archive");
        assertTrue(archiveFolder.exists());

        BufferedReader reader = new BufferedReader(new FileReader(
                folder + File.separator + "archive" + File.separator + "test1.txt"));
        String text = reader.readLine();
        assertEquals("Just first text for testing.", text);

        reader = new BufferedReader(new FileReader(
                folder + File.separator + "archive" + File.separator + "test2.txt"));
        text = reader.readLine();
        assertEquals("Just second text for testing.", text);

        reader = new BufferedReader(new FileReader(
                folder + File.separator + "archive" + File.separator + "test3.txt"));
        text = reader.readLine();
        assertEquals("Just third text for testing.", text);

        File badFile = new File(
                folder + File.separator + "archive" + File.separator + "badTest.txt");
        assertFalse(badFile.exists());

        for (File f : folder.listFiles()) {
            f.delete();
        }
        folder.delete();
    }
}
