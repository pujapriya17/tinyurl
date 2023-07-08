package com.priyanka.tinyurl.dao;

import com.datastax.driver.core.exceptions.AlreadyExistsException;
import com.priyanka.tinyurl.entity.URL;

import java.util.Optional;

public interface UrlDao {

    void addUrl(URL url) throws AlreadyExistsException;

    void deleteUrl(String hash);

    URL getURL(String hash);
}
