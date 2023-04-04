package com.starkodinson;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ServersideLogic {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

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
                    ServersideLogic.tempSingleServer.addUser(newUser);
                    tempSingleServer.broadcastString("||USERLISTUPDATE||*(%#$#$:" + allUsers.stream().map(n -> n.getUsername()).collect(Collectors.joining(";","{","}")));
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
                    tempUS.broadcastString(String.format("%s: %s\n", userToRead.getUsername(), message));
                } catch (IOException e) {
                    System.out.println(ANSI_RED + userToRead.getUsername() + " disconnected." + ANSI_RESET);
                    try {
                        tempSingleServer.broadcastString("||USERLISTUPDATE||*(%#$#$:" + allUsers.stream().map(n -> n.getUsername()).collect(Collectors.joining(";","{","}")));
                        tempUS.broadcastString("<" + userToRead.getUsername() + " disconnected." + ">" + "\n");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    try {
                        userToRead.closeSocket();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }

            ServersideLogic.allUsers.remove(userToRead);
        }
    }
}
