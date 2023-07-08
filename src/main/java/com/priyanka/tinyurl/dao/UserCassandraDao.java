package com.priyanka.tinyurl.dao;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.priyanka.tinyurl.database.DatabaseConnector;
import com.priyanka.tinyurl.entity.URL;
import com.priyanka.tinyurl.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.transform.Result;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserCassandraDao implements UserDao{

    @Autowired
    private DatabaseConnector client;
    private Session session;

    public UserCassandraDao(DatabaseConnector client) {
        client.connect();
        this.session = client.getSession();
    }

    @Override
    public void addUser(User user) {
        session.execute(new StringBuilder("INSERT INTO ")
                .append("tinyurl.user").append("(user_id, creation_date, email, first_name, last_name) ")
                .append("values (").append(user.getUserId())
                .append(", '").append(user.getCreationDate())
                .append("', '").append(user.getEmail())
                .append("', '").append(user.getFirst_name())
                .append("', '").append(user.getLast_name())
                .append("') IF NOT EXISTS;")
                .toString());
    }

    @Override
    public User getUser(UUID uuid) {
        User user = null;

        ResultSet rs = session.execute(new StringBuilder("SELECT * FROM tinyurl.user WHERE user_id = ")
                .append(uuid).append(";")
                .toString());

        for(Row r : rs){
            user = new User(r.getUUID("user_id"),
                    r.getString("first_name"),
                    r.getString("last_name"),
                    r.getString("email"),
                    new Date(r.getDate("creation_date").getMillisSinceEpoch()),
                    r.getTimestamp("last_login"));
        }

        return user;
    }

    @Override
    public User getUser(String emailId) {
        User user = null;

        ResultSet rs = session.execute(new StringBuilder("SELECT * FROM tinyurl.user WHERE email = '")
                .append(emailId).append("';")
                .toString());

        for(Row r : rs){
            user = new User(r.getUUID("user_id"),
                    r.getString("first_name"),
                    r.getString("last_name"),
                    r.getString("email"),
                    new Date(r.getDate("creation_date").getMillisSinceEpoch()),
                    r.getTimestamp("last_login"));
        }

        return user;
    }
}
