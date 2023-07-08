package com.priyanka.tinyurl.serviceImpl;

import com.priyanka.tinyurl.dao.UserDao;
import com.priyanka.tinyurl.entity.User;
import com.priyanka.tinyurl.request.CreateUserRequest;
import com.priyanka.tinyurl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Override
    public User createUser(CreateUserRequest createUserRequest) {
        User user = new User(UUID.randomUUID(), createUserRequest.getFirst_name(), createUserRequest.getLast_name(), createUserRequest.getEmail(), new java.sql.Date(new Date().getTime()), null);
        userDao.addUser(user);
        return user;
    }

    @Override
    public User getUser(UUID uuid) {
        return userDao.getUser(uuid);
    }

    @Override
    public User getUser(String emailId) {
        return userDao.getUser(emailId);
    }
}
