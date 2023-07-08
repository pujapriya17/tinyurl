package com.priyanka.tinyurl.database;

import com.datastax.driver.core.Session;

public interface DatabaseConnector {
    void connect();
    Session getSession();
    void close();
}
