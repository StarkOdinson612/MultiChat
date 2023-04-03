package com.starkodinson;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ServersideLogic {

    private static Set<User> allUsers = new HashSet<>();

    public static UserServer tempSingleServer;

    public static void main(String[] args) throws IOException {
	// write your code here
        ServerSocket sSocket = new ServerSocket(49999);
        tempSingleServer = new UserServer(1);

        Thread accept = new Thread(new AcceptThread(sSocket));
        accept.start();
    }

    static class AcceptThread implements Runnable
    {
        private ServerSocket serverSocket;

        public AcceptThread(ServerSocket serverSocket)
        {
            this.serverSocket = serverSocket;
        }

        @Override
        public void run() {
            while (!serverSocket.isClosed())
            {
                try {
                    Socket s = serverSocket.accept();
                    User newUser = new User(s);
                    allUsers.add(newUser);

                    Thread newUserRead = new Thread(new ReadSingleUserMessages(newUser, ServersideLogic.tempSingleServer));
                    newUserRead.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class ReadSingleUserMessages implements Runnable
    {
        private User userToRead;
        private UserServer tempUS;

        public ReadSingleUserMessages(User userToRead) {
            this.userToRead = userToRead;
        }

        // Temporary single server implementation
        public ReadSingleUserMessages(User uTR, UserServer tUS)
        {
            this.userToRead = uTR;
            this.tempUS = tUS;
        }


        @Override
        public void run() {
            while (userToRead.isConnected())
            {
                try {
                    // Currently using crude string implementation, multi-server version will use Message Objects
                    String message = userToRead.readMessage();
                    System.out.printf("%s: %s\n", userToRead.getUsername(), message);
                    tempUS.broadcastString(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
