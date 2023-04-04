package com.starkodinson;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UserServer {
    private Set<User> userSet;
    private Set<String> bannedIP;
    private String name;
    private User owner;

    private int serverID;

    public Set<String> filteredWords;

    public UserServer(int id) throws FileNotFoundException {
        userSet = new HashSet<User>();
        bannedIP = new HashSet<>();

        filteredWords = new HashSet<>();

        this.serverID = id;

    }

    public int addUser(User user) {
        if (bannedIP.contains(user.getUserIP())) { return -1; }
        userSet.add(user);
        return 0;
    }

    public List<User> getUL()
    {
        return new ArrayList<>(userSet);
    }

    public void addBannedWord(String word) { filteredWords.add(word); }

    public boolean containsBannedWord(String message) { return filteredWords.stream().anyMatch(message::contains); }

    public void banUser(User user) { bannedIP.add(user.getUserIP()); }

//    public void broadcastMessage(Message message) throws IOException {
//        for (User user : userSet)
//        {
//            user.sendMessage(message);
//        }
//    }

    public void broadcastString(String message) throws IOException
    {
        for (User user : userSet)
        {
            user.sendString(message);
        }
    }

    public boolean containsUser(User user)
    {
        return userSet.stream().map(i -> i.getUserIP()).collect(Collectors.toSet()).contains(user.getUserIP());
    }

    public int getServerID() { return this.serverID; }
}
