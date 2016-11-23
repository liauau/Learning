package com.wow.learning;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by ljinjin on 11/18/16.
 */

public class Server {
    private static final String ADDR = "127.0.0.1";
    private static final int PORT = 60000;

    public void main(String[] args) {
        start();
    }

    private void start() {
        try {
            ServerSocket server = new ServerSocket(PORT);
            server.getInetAddress();
            Socket socket = server.accept();
            DataInputStream is = new DataInputStream(socket.getInputStream());
            PrintStream os = new PrintStream(socket.getOutputStream());
                while (true) {
                    try {
                        os.println(is.readLine());
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
