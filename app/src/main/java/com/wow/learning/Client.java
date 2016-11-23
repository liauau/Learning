package com.wow.learning;

import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by ljinjin on 11/18/16.
 */

public class Client {
    public void start() {
        Socket socket = new Socket();
        SocketAddress sa = socket.getRemoteSocketAddress();
        try {
            socket.connect(sa);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
