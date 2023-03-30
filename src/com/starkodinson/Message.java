package com.starkodinson;

public class Message {
    private String contents;

    private User sender;
    private UserServer destination;

    public Message(String contents, User sender, UserServer dest)
    {
        this.contents = contents;
        this.sender = sender;
        this.destination = dest;
    }

    public String getContents() { return contents; }

    public String getSenderIP() { return sender.getUserIP(); }

    public int getDestinationID() { return destination.getServerID(); }
}
