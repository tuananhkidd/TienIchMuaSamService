package com.kidd.shopping.utils;

public class PasswordValidator {
    public static final String PATTERN = ".{6,}";

    public static boolean isPasswordValid(String password) {
        return password.matches(PATTERN);
    }
}
