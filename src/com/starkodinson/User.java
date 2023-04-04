package com.starkodinson;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class User {
    private Socket userSocket;
    private String username;
    private String userIP;
    private int port;

    private BufferedReader userInputStream;
    private ObjectOutputStream userOOS;
    private PrintWriter userOutputStream;

    public User(Socket s) throws IOException {
        userSocket = s;
        userIP = userSocket.getInetAddress().toString();
        port = userSocket.getPort();

        userInputStream = new BufferedReader(new InputStreamReader(userSocket.getInputStream()));

        String message = userInputStream.readLine();
        System.out.println(message);
        this.username = message;

        userOutputStream = new PrintWriter(userSocket.getOutputStream(), true);
        userOOS = new ObjectOutputStream(userSocket.getOutputStream());
    }

    public String getUserIP() { return userIP; }
    public String getUsername() { return username; }

    public String readMessage() throws IOException {
        return userInputStream.readLine();
    }

    public void sendString(String message) throws IOException {
        userOutputStream.println(message);
    }

//    public void sendMessage(Message message) {
//
//    }

    public boolean isConnected() { return !userSocket.isClosed(); }

    public void closeSocket() throws IOException { userSocket.close(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username) && userIP.equals(user.userIP);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, userIP);
    }
}
