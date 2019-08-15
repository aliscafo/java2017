package ru.spbau.erokhina.hw10;

import org.junit.Test;

import java.awt.image.AreaAveragingScaleFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.Assert.*;

public class SecondPartTasksTest {
    void createFiles (File folder) throws IOException {
        File newFile1 = new File(folder.getAbsolutePath() + File.separator + "file1.txt");
        newFile1.createNewFile();

        try(FileWriter writer = new FileWriter(newFile1)) {
            writer.write("ABC\n");
            writer.write("ABA(find)CABA\n");
            writer.write("NNNO(find)DDDD\n");

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        File newFile2 = new File(folder.getAbsolutePath() + File.separator + "file2.txt");
        newFile2.createNewFile();

        try(FileWriter writer = new FileWriter(newFile2)) {
            writer.write("YYY(find)C\n");
            writer.write("(find)\n");
            writer.write("FGTRYHVE\n");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    void deleteFiles(File folder) {
        for(File f : folder.listFiles())
            f.delete();
        folder.delete();
    }

    @Test
    public void testFindQuotes() throws Exception {
        File folder = new File(System.getProperty("user.dir") + File.separator + "testData");
        folder.mkdir();

        createFiles(folder);

        List<String> expected = Arrays.asList("ABA(find)CABA", "NNNO(find)DDDD",
                "YYY(find)C", "(find)");

        List<String> paths = Arrays.asList(folder.getName() + File.separator + "file1.txt",
                folder.getName() + File.separator + "file2.txt");

        ArrayList<String> lst = (ArrayList<String>) SecondPartTasks.findQuotes(paths, "(find)");

        assertEquals(expected.size(), lst.size());

        for (int i = 0; i < expected.size(); i++)
            assertTrue(lst.get(i).equals(expected.get(i)));

        deleteFiles(folder);
    }

    @Test
    public void testPiDividedBy4() throws Exception {
        assertEquals(Math.PI / 4, SecondPartTasks.piDividedBy4(), 0.001);
    }

    @Test
    public void testFindPrinter() throws Exception {
        Map<String, List<String>> compositions = new HashMap<>();
        compositions.put("Chopin", Arrays.asList("Etude", "Sonata", "Ballad", "Mazurka"));
        compositions.put("Bach", Arrays.asList("Cantata", "Chorale", "Concerto", "Suite"));
        compositions.put("Beethoven", Arrays.asList("Piano Concerto", "Quintet", "Piano Sonata \"Moonlight\""));
        compositions.put("Mozart", Arrays.asList("Symphony", "Sonata", "Serenade", "Menuet"));

        assertEquals("Beethoven", SecondPartTasks.findPrinter(compositions));
    }

    @Test
    public void testCalculateGlobalOrder() throws Exception {
        List<Map<String, Integer>> orders = new ArrayList<>();

        Map<String, Integer> company1 = new HashMap<>();
        company1.put("apple", 50);
        company1.put("milk", 70);
        company1.put("banana", 40);
        orders.add(company1);

        Map<String, Integer> company2 = new HashMap<>();
        company2.put("apple", 90);
        company2.put("chocolate", 30);
        company2.put("yogurt", 10);
        company2.put("fish", 50);
        orders.add(company2);

        Map<String, Integer> company3 = new HashMap<>();
        company3.put("chocolate", 20);
        company3.put("fish", 30);
        company3.put("milk", 20);
        company3.put("yogurt", 40);
        orders.add(company3);

        Map<String, Integer> company4 = new HashMap<>();
        company4.put("apple", 50);
        company4.put("yogurt", 70);
        company4.put("milk", 80);
        orders.add(company4);

        Map<String, Integer> total = SecondPartTasks.calculateGlobalOrder(orders);

        assertTrue(total.containsKey("apple"));
        assertTrue(total.get("apple").equals(190));

        assertTrue(total.containsKey("milk"));
        assertTrue(total.get("milk").equals(170));

        assertTrue(total.containsKey("banana"));
        assertTrue(total.get("banana").equals(40));

        assertTrue(total.containsKey("chocolate"));
        assertTrue(total.get("chocolate").equals(50));

        assertTrue(total.containsKey("yogurt"));
        assertTrue(total.get("yogurt").equals(120));

        assertTrue(total.containsKey("fish"));
        assertTrue(total.get("fish").equals(80));
    }
}