package com.kidd.shopping.utils;

import java.util.Base64;

/**
 * Dùng để encode và decode theo Base64
 */
public class Base64Utils {

    /**
     * Phương thức decode string
     * @param base64EncodedString string cần decode
     * @return string đã được decode
     */
    public static String decode(String base64EncodedString) {
        byte[] bytes = Base64.getDecoder().decode(base64EncodedString);
        return new String(bytes);
    }

    /**
     * Phương thức encode string
     * @param value string cần encode
     * @return string đã được encode
     */
    public static String encode(String value) {
        byte[] bytes = Base64.getEncoder().encode(value.getBytes());
        return new String(bytes);
    }
}
