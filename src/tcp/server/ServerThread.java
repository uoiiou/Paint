package tcp.server;

import tcp.Control;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerThread extends Thread {
    private static DataOutputStream dataOutputStream;
    private static ServerSocket serverSocket;
    private static Socket socket;
    private static int i = 0;
    private static boolean bsend = false;

    public ServerThread() {}

    public void run() {
        try {
            serverSocket = new ServerSocket(10001);
            System.out.println("Server started");

            socket = serverSocket.accept();
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            System.out.println("(ServerThread) - " + ex);
        }
    }

    public static void send(double x, double y, String color, double bsize, boolean connectpoints){
        if (bsend)
            try{
                dataOutputStream.writeDouble(x);
                dataOutputStream.writeDouble(y);
                dataOutputStream.writeUTF(color);
                dataOutputStream.writeDouble(bsize);
                dataOutputStream.writeBoolean(connectpoints);

                System.out.println("Server - " + x + " " + y + " " + color + " " + bsize + " count = " + i++);
            } catch(IOException ex)
            {
                bsend = false;
                System.out.println("(ServerThread) - " + ex);
            }
    }

    public static void stopserver(){
        try {
            dataOutputStream.close();
            socket.close();
            serverSocket.close();
            System.out.println("Server close");
            Thread.currentThread().interrupt();
            Control.setExit(true);
        } catch (IOException e) {
            System.out.println("(ServerThread) - " + e);
        }
    }

    public static void setBsend(boolean flag){
        bsend = flag;
    }
}
