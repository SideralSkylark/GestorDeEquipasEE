package com.example.gestordeequipasee.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUtil {

    // Hash a plain-text password
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Check if the plain-text password matches the hashed password
    public static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }
}
