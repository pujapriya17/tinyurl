package com.priyanka.tinyurl.controller;

import com.priyanka.tinyurl.entity.User;
import com.priyanka.tinyurl.request.CreateUserRequest;
import com.priyanka.tinyurl.request.GetUserRequest;
import com.priyanka.tinyurl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody CreateUserRequest createUserRequest){
        User user = userService.createUser(createUserRequest);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<User> getUser(@PathVariable UUID id){
//        User user = userService.getUser(id);
//        return new ResponseEntity<>(user, HttpStatus.OK);
//    }

    @PostMapping("/get")
    public ResponseEntity<User> getUser(@RequestBody GetUserRequest getUserRequest){
        System.out.println("email Id in request: " + getUserRequest.getEmail_id());
        User user = userService.getUser(getUserRequest.getEmail_id());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
