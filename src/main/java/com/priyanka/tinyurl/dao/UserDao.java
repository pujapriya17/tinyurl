package com.priyanka.tinyurl.dao;

import com.priyanka.tinyurl.entity.User;

import java.util.UUID;

public interface UserDao{

    void addUser(User user);

    User getUser(UUID uuid);

    User getUser(String emailId);
}
