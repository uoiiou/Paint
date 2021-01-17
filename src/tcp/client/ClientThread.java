package tcp.client;

import tcp.Control;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    private static Socket socket;
    private static DataInputStream dataInputStream;

    public void run(){
        try {
            socket = new Socket("127.0.0.1", 10001);
            System.out.println("Client connected (" + socket.getInetAddress() + "  " + socket.getPort() + ")");
            dataInputStream = new DataInputStream(socket.getInputStream());


            while (true)
            {
                Control.getInstance().setPoint(
                        dataInputStream.readDouble(),
                        dataInputStream.readDouble(),
                        dataInputStream.readUTF(),
                        dataInputStream.readDouble(),
                        dataInputStream.readBoolean());
            }
        } catch ( IOException ex) {
            System.out.println("(ClientThread - Error connecting to server) - " + ex);
        }
    }

    public static void stopclient(){
        try {
            dataInputStream.close();
            socket.close();
            System.out.println("Client disconnected");
            Thread.currentThread().interrupt();
        } catch (IOException e) {
            System.out.println("(ClientThread) - " + e);
        }
    }
}
