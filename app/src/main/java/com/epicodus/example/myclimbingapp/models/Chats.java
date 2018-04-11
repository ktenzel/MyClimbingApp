package com.epicodus.example.myclimbingapp.models;

import org.parceler.Parcel;

import java.util.HashMap;
import java.util.Map;

@Parcel
public class Chats {
    Map<String, Boolean> userIds = new HashMap<>();
    Map<String, Messages> messages = new HashMap<>();
    String pushId;

    public Chats() {}

    public Chats(Map<String, Boolean> userIds) {
        this.userIds = userIds;
    }

    public Map<String, Boolean> getUserIds() {
        return userIds;
    }

    public void setUserIds(Map<String, Boolean> userIds) {
        this.userIds = userIds;
    }

    public Map<String, Messages> getMessages() {
        return messages;
    }

    public void setMessages(Map<String, Messages> messages) {
        this.messages = messages;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
