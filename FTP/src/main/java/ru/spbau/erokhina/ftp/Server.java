package ru.spbau.erokhina.ftp;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * Class that provides a server for processing requests of two types:
 * 1. list - for listing files in particular directory;
 * 2. get - for getting a content of specified file.
 * Server can work with several clients.
 */
public class Server {
    private static final int serverPort = 7777;
    private ServerSocket serverSocket;
    private boolean flag;
    private final int POOL_SIZE = 50;

    /**
     * Method for launching a server.
     */
    public void start() throws IOException {
        ExecutorService poolExecutor = Executors.newFixedThreadPool(POOL_SIZE);;

        flag = true;
        try {
            serverSocket = new ServerSocket(serverPort);
        } catch (Exception e) {
            return;
        }

        new Thread(()->{
            try {
                while (flag) {
                    Socket socket = serverSocket.accept();
                    try {
                        //new OneThreadedServer(socket);
                        poolExecutor.submit(new ServerHandler(socket));
                    } catch (IOException e) {
                        e.printStackTrace();
                        socket.close();
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    /**
     * Method for stopping a server.
     */
    public void stop() {
        flag = false;
    }

    private class ServerHandler implements Runnable {
        private Socket socket;
        private DataInputStream in;
        private DataOutputStream out;
        ServerHandler(Socket socket) throws IOException {
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            start();
        }

        public void run () {
            try {
                while (true) {
                    int queryType;
                    String path;

                    try {
                        queryType = in.readInt();
                        path = in.readUTF();

                        if (queryType == 1) {
                            listQuery(path);
                        } else if (queryType == 2) {
                            getBytesQuery(path);

                        } else {
                            break;
                        }

                    } catch (Exception e) {
                        break;
                    }
                }

            }
            finally {
                try {
                    socket.close();
                }
                catch (IOException e) {
                    System.err.println("Socket not closed");
                }
            }
        }

        void listQuery(String path) throws IOException {
            List<Path> list;

            try {
                list = Files.list(Paths.get(path)).collect(Collectors.toList());
            } catch (Exception e) {
                list = new ArrayList<>();
            }

            out.writeInt(list.size());

            for (Path filePath : list) {
                out.writeUTF(filePath.getFileName().toString());

                out.writeBoolean(Files.isDirectory(filePath));
            }

            out.flush();
        }

        void getBytesQuery(String path) throws IOException {
            long sizeFile;
            FileInputStream fileIn = null;

            try {
                fileIn = new FileInputStream(path);
                sizeFile = Files.size(Paths.get(path));
            } catch (Exception e) {
                sizeFile = 0;
            }

            ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
            buffer.putLong(sizeFile);
            byte[] sizeFileArray = buffer.array();

            IOUtils.writeChunked(sizeFileArray, out);

            byte[] buff = new byte[1024];
            long cur = 0;
            while (cur < sizeFile) {
                int toRead = (int) Math.min(1024, sizeFile - cur);

                fileIn.read(buff, 0, toRead);
                out.write(buff, 0, toRead);
                cur += toRead;
            }

            out.flush();
        }
    }

    /**
     * Method for launching server from console.
     */
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.start();
    }
}
