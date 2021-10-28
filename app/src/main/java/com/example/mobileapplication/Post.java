package com.example.mobileapplication;

import java.io.File;

public class Post {

    private int profileImage; // ProfileImage to store the resource id
    private String userName;
    private File audioTrack; // audio

    public Post(int profileImage, String userName, File audioTrack) {
        this.profileImage = profileImage;
        this.userName = userName;
        this.audioTrack = audioTrack;
    }

    public int getProfileImage() {
        return profileImage;
    }


    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public File getAudioTrack() {
        return audioTrack;
    }

    public void setAudioTrack(File audioTrack) {
        this.audioTrack = audioTrack;
    }
}
