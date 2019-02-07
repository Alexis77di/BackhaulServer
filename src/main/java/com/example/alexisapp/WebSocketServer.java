package com.example.alexisapp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

class WebSocketServer {
    static void send(String pathName, int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();
        System.out.println("Accepted connection : " + socket);
        File transferFile = new File(pathName);
        byte[] bytearray = new byte[(int) transferFile.length()];
        FileInputStream fin = new FileInputStream(transferFile);
        BufferedInputStream bin = new BufferedInputStream(fin);
        bin.read(bytearray);
        OutputStream os = socket.getOutputStream();
        System.out.println("Sending Files...");
        os.write(bytearray);
        os.flush();
        socket.close();
        System.out.println("File transfer complete");
    }

    public static String recieve(String host, int port) {
        try {
            Socket socket = new Socket(host, port);
            InputStream is = socket.getInputStream();
            byte[] buffer = new byte[8192]; // or 4096, or more
            is.read(buffer);
            socket.close();
            return new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void answer(int status, int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();
        System.out.println("Accepted connection : " + socket);
        OutputStream os = socket.getOutputStream();
        System.out.println("Sending Files...");
        os.write(status);
        os.flush();
        socket.close();
        System.out.println("File transfer complete");
    }
}
