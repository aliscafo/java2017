package ru.spbau.erokhina.ftp;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Console application for responses to the requests of two types:
 * 1. list - for listing files in particular directory;2
 * 2. get - for getting a content of specified file.
 */
public class Application {
    private static final int serverPort = 7777;
    private static final String address = "127.0.0.1";

    public static void main(String[] args) {
        try {
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            String line = null;
            System.out.println("Enter the query:");
            System.out.println();

            Client client = new Client(address, serverPort);

            while (true) {
                line = keyboard.readLine();

                String[] words = line.split(" ");

                if (words.length != 2) {
                    System.out.println("Unknown query! Try:");
                    System.out.println("<1: Int> <path: String> - listing");
                    System.out.println("<2: Int> <path: String> - getting content of file");
                    continue;
                }

                String path = words[1];

                switch (words[0]) {
                    case "1":
                        HashMap<String, Boolean> map = client.list(path);

                        System.out.println(map.size());

                        for (Map.Entry<String, Boolean> entry : map.entrySet()) {
                            System.out.println(entry.getKey() + " " + entry.getValue());
                        }
                        break;
                    case "2":
                        byte[] bytes = client.get(path);

                        System.out.println(bytes.length);

                        for (byte aByte : bytes) {
                            System.out.print(aByte + " ");
                        }
                        System.out.println();

                        break;
                    default:
                        System.out.println("Unknown query! Try:");
                        System.out.println("<1: Int> <path: String> - listing");
                        System.out.println("<2: Int> <path: String> - getting content of file");
                }


            }
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}
