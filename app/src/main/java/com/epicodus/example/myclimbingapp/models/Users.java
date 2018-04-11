package com.epicodus.example.myclimbingapp.models;

import org.parceler.Parcel;

@Parcel
public class Users {
    String userName;
    String uid;
    String profilePic;

    public Users(){}

    public Users(String userName, String uid, String profilePic){
        this.userName = userName;
        this.uid = uid;
        this.profilePic = profilePic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
