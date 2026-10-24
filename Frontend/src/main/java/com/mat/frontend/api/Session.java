package com.mat.frontend.api;

import com.mat.frontend.api.dto.LoginResponse;

public class Session {
    public static LoginResponse currentUser;

    public static void setCurrentUser(LoginResponse user) {
        currentUser = user;
    }

    public static LoginResponse getCurrentUser() {
        return currentUser;
    }

    public static void clear() {
        currentUser = null;
    }
}
