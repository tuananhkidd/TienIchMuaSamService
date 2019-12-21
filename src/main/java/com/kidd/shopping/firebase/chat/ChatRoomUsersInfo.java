package com.kidd.shopping.firebase.chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatRoomUsersInfo {
    public static final String TYPING_STATES = "typingStates";

    private Map<String, ChatUser> usersInfo = new HashMap<>();

    public ChatRoomUsersInfo(Map<String, ChatUser> userInfo) {
       this.usersInfo.clear();
       this.usersInfo= (userInfo);
    }

    public ChatRoomUsersInfo() {
    }

    public Map<String, ChatUser> getUsersInfo() {
        return usersInfo;
    }

    public void setUsersInfoList(Map<String, ChatUser>  usersInfo) {
        this.usersInfo = usersInfo;
    }
}
