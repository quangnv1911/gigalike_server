package com.gigalike.auth.util;

import java.util.UUID;

public class UserUtil {

    public static String generateUserToken(String userName) {
        return userName + "-" + UUID.randomUUID().toString().replace("-", "");
    }
}
