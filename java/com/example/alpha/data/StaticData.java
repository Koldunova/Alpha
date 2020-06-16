package com.example.alpha.data;

import com.example.alpha.domain.User;

public final class StaticData {

    private static User user;
    private static final String TAG="MyLogger";

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        StaticData.user = user;
    }

    public static String getTAG() {
        return TAG;
    }
}
