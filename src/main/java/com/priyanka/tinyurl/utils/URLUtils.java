package com.priyanka.tinyurl.utils;

import org.springframework.util.DigestUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class URLUtils {
    public static String getEncryptedString(String s, MessageDigestAlgorithm algorithm) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm.toString());

        return DigestUtils.md5DigestAsHex(md.digest(s.getBytes()));
    }

}
