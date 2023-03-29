package com.starkodinson;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.*;

public class UserServer {
    private Set<User> userSet;
    private Set<String> bannedIP;
    private String name;
    private User owner;

    public Set<String> filteredWords;

    public UserServer()
    {
        userSet = new HashSet<User>();
        bannedIP = new HashSet<>();

        filteredWords = new HashSet<>();
    }

    public int addUser(User user) {
        if (bannedIP.contains(user.getUserIP())) { return -1; }
        userSet.add(user);
        return 0;
    }

    public void addBannedWord(String word) { filteredWords.add(word); }

    public void banUser(User user) { bannedIP.add(user.getUserIP()); }

    public void broadcastMessage(String message) throws IOException {
        for (User user : userSet)
        {
            user.sendMessage(message);
        }
    }



    class ReadMessages implements Runnable
    {
        @Override
        public void run() {

        }
    }
}
