package com.starkodinson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.Buffer;

public class User {
    private Socket userSocket;
    private String username;
    private String userIP;
    private int port;

    private BufferedReader userInputStream;
    private PrintWriter userOutputStream;

    public User(Socket s) throws IOException {
        userSocket = s;
        userIP = userSocket.getInetAddress().toString();
        port = userSocket.getPort();

        userInputStream = new BufferedReader(new InputStreamReader(userSocket.getInputStream()));
        userOutputStream = new PrintWriter(userSocket.getOutputStream(), true);
    }

    public String getUserIP() { return userIP; }
    public String getUsername() { return username; }

    public String readMessage() throws IOException {
        return userInputStream.readLine();
    }

    public void sendMessage(String message) throws IOException {
        userOutputStream.println(message);
    }


}
