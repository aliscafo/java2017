package ru.spbau.erokhina.ftp_test;

import org.junit.*;
import org.junit.rules.TemporaryFolder;
import ru.spbau.erokhina.ftp.Client;
import ru.spbau.erokhina.ftp.Server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import static org.junit.Assert.*;

public class FTPTest {
    private static Server server = new Server();

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @BeforeClass
    public static void before() throws IOException {
        server.start();
    }

    /**
     * Testing simple case for list query.
     */
    @Test
    public void testList() throws Exception {
        Client client = new Client("localhost", 7777);
        folder.newFile("a.txt");
        folder.newFile("b.txt");
        folder.newFile("c.txt");
        folder.newFolder("ddd");

        HashMap<String, Boolean> hm = client.list(folder.getRoot().getAbsolutePath());

        assertEquals(4, hm.size());

        assertTrue(hm.containsKey("a.txt"));
        assertTrue(hm.containsKey("b.txt"));
        assertTrue(hm.containsKey("c.txt"));
        assertTrue(hm.containsKey("ddd"));
        assertFalse(hm.get("a.txt"));
        assertFalse(hm.get("b.txt"));
        assertFalse(hm.get("c.txt"));
        assertTrue(hm.get("ddd"));

        client.closeAll();
    }

    /**
     * Testing simple case for get query.
     */
    @Test
    public void testGet() throws IOException {
        Client client = new Client("localhost", 7777);

        File file = folder.newFile("a.txt");
        Files.write(file.toPath(), "test".getBytes());

        assertArrayEquals("test".getBytes(), client.get(file.toPath().toString()));

        client.closeAll();
    }

    /**
     * Testing case of two clients for get query.
     */
    @Test
    public void testGetForTwoClients() throws IOException {
        Client client1 = new Client("localhost", 7777);
        Client client2 = new Client("localhost", 7777);

        File file = folder.newFile("a.txt");
        Files.write(file.toPath(), "test".getBytes());

        assertArrayEquals("test".getBytes(), client1.get(file.toPath().toString()));
        assertArrayEquals("test".getBytes(), client2.get(file.toPath().toString()));

        client1.closeAll();
        client2.closeAll();
    }

    /**
     * Testing case of several clients for list query.
     */
    @Test
    public void testListForMultiClients() throws Exception {
        Client[] clients = new Client[50];

        for (int i = 0; i < 50; i++) {
            clients[i] = new Client("localhost", 7777);
        }

        folder.newFile("a.txt");
        folder.newFile("b.txt");
        folder.newFile("c.txt");
        folder.newFolder("ddd");

        for (int i = 0; i < 50; i++) {
            HashMap<String, Boolean> hm = clients[i].list(folder.getRoot().getAbsolutePath());

            assertEquals(4, hm.size());

            assertTrue(hm.containsKey("a.txt"));
            assertTrue(hm.containsKey("b.txt"));
            assertTrue(hm.containsKey("c.txt"));
            assertTrue(hm.containsKey("ddd"));
            assertFalse(hm.get("a.txt"));
            assertFalse(hm.get("b.txt"));
            assertFalse(hm.get("c.txt"));
            assertTrue(hm.get("ddd"));
        }

        for (int i = 0; i < 50; i++) {
            clients[i].closeAll();
        }
    }

    /**
     * Testing case of non-existent for get query.
     */
    @Test
    public void testGetNonExistentFileName() throws IOException {
        Client client = new Client("localhost", 7777);

        File file = folder.newFile("a.txt");
        Files.write(file.toPath(), "test".getBytes());

        byte[] array = client.get("nonExistentFileName");

        assertTrue(array.length == 0);

        client.closeAll();
    }

    /**
     * Testing case of non-existent for list query.
     */
    @Test
    public void testListNonExistentFileName() throws IOException {
        Client client = new Client("localhost", 7777);

        File file = folder.newFile("a.txt");
        Files.write(file.toPath(), "test".getBytes());

        HashMap<String, Boolean> hm = client.list("nonExistentFileName");

        assertTrue(hm.size() == 0);

        client.closeAll();
    }

    @AfterClass
    public static void after() throws IOException {
        server.stop();
    }
}