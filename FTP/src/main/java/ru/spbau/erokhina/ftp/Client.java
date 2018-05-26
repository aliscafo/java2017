package ru.spbau.erokhina.ftp;

import java.net.InetAddress;

import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.HashMap;
import org.apache.commons.io.IOUtils;

/**
 * Class which provides a client that executes requests of two types:
 * 1. list - for listing files in particular directory;
 * 2. get - for getting a content of specified file.
 */
public class Client {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private static final int BUFFER_SIZE = 1024;

    /**
     * Constructor for a Client.
     * @param host host address
     * @param port given port
     */
    public Client(String host, int port) throws IOException {
        InetAddress address = InetAddress.getByName(host);

        socket = new Socket(address, port);

        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        }
        catch (Exception e) {
            socket.close();
            throw e;
        }
    }

    /**
     * Method for a "list" query.
     * @param path given path of a file
     * @return a map of file names as keys and flags (if file is a directory) as values.
     */
    public HashMap<String, Boolean> list(String path) throws IOException {
        HashMap<String, Boolean> res = new HashMap<>();
        out.writeInt(1);
        out.writeUTF(path);
        out.flush();
        int size = in.readInt();
        for (int i = 0; i < size; i++) {
            String name = in.readUTF();
            Boolean isDir = in.readBoolean();

            res.put(name, isDir);
        }

        return res;
    }

    /**
     * Method for a "get" query.
     * @param path given path of a file
     * @return a file content as byte array
     */
    public byte[] get(String path) throws IOException {
        out.writeInt(2);
        out.writeUTF(path);
        out.flush();

        byte[] sizeFileArray = IOUtils.readFully(in, 8);
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.put(sizeFileArray);
        buffer.flip();

        long sizeFile = buffer.getLong();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[BUFFER_SIZE];
        long cur = 0;
        while (cur < sizeFile) {
            int toRead = (int) Math.min(BUFFER_SIZE, sizeFile - cur);

            in.read(buff, 0, toRead);
            baos.write(buff, 0, toRead);
            cur += toRead;
        }

        return baos.toByteArray();
    }

    /**
     * Method for closing socket and streams.
     */
    public void closeAll() throws IOException {
        socket.close();
        in.close();
        out.close();
    }

}
