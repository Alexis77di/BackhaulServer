package com.example.alexisapp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class WebSocket {
    public static void main(String[] args) {
        try {
            new WebSocket().receive("127.0.0.1", 15123);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        Socket socket = serverSocket.accept();
        System.out.println("Accepted connection : " + socket);
        File transferFile = new File("test.csv");
        byte[] bytearray = new byte[(int) transferFile.length()];
        FileInputStream fin = new FileInputStream(transferFile);
        BufferedInputStream bin = new BufferedInputStream(fin);
        bin.read(bytearray, 0, bytearray.length);
        OutputStream os = socket.getOutputStream();
        System.out.println("Sending Files...");
        os.write(bytearray, 0, bytearray.length);
        os.flush();
        socket.close();
        System.out.println("File transfer complete");
    }

    public void receive(String host, int port) throws IOException {
        int filesize = 1022386;
        Socket socket = new Socket(host, port);
        byte[] bytearray = new byte[filesize];
        InputStream is = socket.getInputStream();
        FileOutputStream fos = new FileOutputStream("copy.csv");
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        int bytesRead = is.read(bytearray, 0, bytearray.length);
        int currentTot = bytesRead;
        do {
            bytesRead = is.read(bytearray, currentTot, (bytearray.length - currentTot));
            if (bytesRead >= 0)
                currentTot += bytesRead;
        } while (bytesRead > -1);
        bos.write(bytearray, 0, currentTot);
        bos.flush();
        bos.close();
        socket.close();
    }
}
