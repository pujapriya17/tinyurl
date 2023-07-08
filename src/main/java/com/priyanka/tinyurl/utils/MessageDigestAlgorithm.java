package com.priyanka.tinyurl.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Confirms to https://docs.oracle.com/en/java/javase/11/docs/specs/security/standard-names.html#messagedigest-algorithms
 */
public enum MessageDigestAlgorithm {

    MD2("MD2"), MD5("MD5"), SHA1("SHA-1"), SHA224("SHA-224"),
    SHA256("SHA-256"), SHA384("SHA-384"), SHA512224("SHA-512/224"),
    SHA512256("SHA-512/256"), SHA3224("SHA3-224"), SHA3256("SHA3-256"),
    SHA3384("SHA3-384"), SHA3512("SHA3-512");

    private String value;
    MessageDigestAlgorithm(String value){
        this.value = value;
    }
    private static Map<String, MessageDigestAlgorithm> map = new HashMap<>();

    static{
        for(MessageDigestAlgorithm messageDigestAlgorithm : MessageDigestAlgorithm.values()){
            map.put(messageDigestAlgorithm.value, messageDigestAlgorithm);
        }
    }

    public static MessageDigestAlgorithm getAlgorithm(String value){
        return map.get(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
