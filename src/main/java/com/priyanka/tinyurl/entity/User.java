package com.priyanka.tinyurl.entity;

import java.util.Date;
import java.util.UUID;

public class User {
    private UUID userId;

    private String first_name;
    private String last_name;

    private String email;

    private Date creationDate;

    private Date lastLogin;

    public User() {
    }

    public User(UUID userId, String firstName, String lastName, String email, Date creationDate, Date lastLogin) {
        this.userId = userId;
        this.first_name = firstName;
        this.last_name = lastName;
        this.email = email;
        this.creationDate = creationDate;
        this.lastLogin = lastLogin;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
}
