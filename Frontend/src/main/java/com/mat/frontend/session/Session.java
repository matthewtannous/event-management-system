package com.mat.frontend.session;

import com.mat.frontend.api.dto.LoginResponse;

public class Session {
    private static LoginResponse currentUser;

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
