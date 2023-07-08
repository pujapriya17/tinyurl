package com.priyanka.tinyurl.service;

import com.priyanka.tinyurl.entity.User;
import com.priyanka.tinyurl.request.CreateUserRequest;

import java.util.UUID;

public interface UserService {

    User createUser(CreateUserRequest createUserRequest);

    User getUser(UUID uuid);

    User getUser(String emailId);

}
