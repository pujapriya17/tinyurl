package com.priyanka.tinyurl.utils;

public class ArgumentChecks {

    public static boolean nullOrEmpty(String original_url) {
        return original_url == null || original_url.isEmpty();
    }
}
