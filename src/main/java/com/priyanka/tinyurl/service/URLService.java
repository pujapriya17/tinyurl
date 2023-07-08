package com.priyanka.tinyurl.service;

import com.priyanka.tinyurl.request.CreateURLRequest;
import com.priyanka.tinyurl.request.DeleteURLRequest;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;

public interface URLService {

    String getShortURL(CreateURLRequest createURLRequest) throws NoSuchAlgorithmException;

    Optional<String> getLongURL(String shortURL);

    void deleteURL(DeleteURLRequest deleteURLRequest);
}
