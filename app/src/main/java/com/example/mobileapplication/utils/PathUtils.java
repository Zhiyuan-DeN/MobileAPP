package com.example.mobileapplication.utils;

import java.io.File;

public class PathUtils {

    public static String getBasePath() {
        return "data/data/com.example.mobileapplication/";
    }

    public static String getRecordPath() {
        return getBasePath() + "record/";
    }

    public static String getDownloadPath() {
        return getBasePath() + "cloudAudio/";
    }

    public static File getFileWithCreate(String parentPath, String file) {
        File parent = new File(parentPath);
        if (!parent.exists()) {
            parent.mkdir();
        }
        return new File(parentPath + "/" + file);
    }
}
