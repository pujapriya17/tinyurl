package com.priyanka.tinyurl.exception;

public class RateLimitingException extends Exception{
    public RateLimitingException(String message) {
        super(message);
    }
}
