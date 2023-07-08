package com.priyanka.tinyurl.serviceImpl;

import com.priyanka.tinyurl.exception.RateLimitingException;
import com.priyanka.tinyurl.service.RateLimitingService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RateLimitingServiceImpl implements RateLimitingService {

    private Map<String, Integer> apiDevKeyRateLimitMap = new HashMap<>();
    private Map<String, Integer> apiDevKeyAccessCountMap = new HashMap<>();

    {
        apiDevKeyRateLimitMap.put("abcd", 3);
    }

    @Override
    public void validateApiDevKey(String apiDevKey) throws RateLimitingException {
        if(!apiDevKeyAccessCountMap.containsKey(apiDevKey)){
            apiDevKeyAccessCountMap.put(apiDevKey, 1);
        }else {
            if (apiDevKeyAccessCountMap.get(apiDevKey) >= apiDevKeyRateLimitMap.get(apiDevKey)) {
                throw new RateLimitingException("API dev key crossed max allowed threshold");
            } else {
                apiDevKeyAccessCountMap.put(apiDevKey, apiDevKeyAccessCountMap.get(apiDevKey) + 1);
            }
        }
    }
}
