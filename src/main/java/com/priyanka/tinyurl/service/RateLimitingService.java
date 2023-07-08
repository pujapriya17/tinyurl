package com.priyanka.tinyurl.service;

import com.priyanka.tinyurl.exception.RateLimitingException;

public interface RateLimitingService {
    void validateApiDevKey(String api_dev_key) throws RateLimitingException;
}
