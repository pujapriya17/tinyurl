package com.priyanka.tinyurl.dao;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.AlreadyExistsException;
import com.priyanka.tinyurl.database.DatabaseConnector;
import com.priyanka.tinyurl.entity.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class UrlCassandraDao implements UrlDao{

    private Logger logger = LoggerFactory.getLogger(UrlCassandraDao.class);

    @Autowired
    private DatabaseConnector client;
    private Session session;

    public UrlCassandraDao(DatabaseConnector client){
        client.connect();
        this.session = client.getSession();
    }

    @Override
    public void addUrl(URL url) throws AlreadyExistsException{
        boolean inserted = false;
        ResultSet rs = session.execute(new StringBuilder("INSERT INTO ")
                        .append("tinyurl.url").append("(hash, creation_date, expiration_date, original_url, user_id) ")
                        .append("values ('").append(url.getHash())
                        .append("', '").append(url.getCreationDate())
                        .append("', '").append(url.getExpirationDate())
                        .append("', '").append(url.getOriginalURL())
                        .append("', ").append(url.getUserId())
                        .append(") IF NOT EXISTS;")
                        .toString());

        for(Row r : rs){
            inserted = r.getBool("[applied]");
        }

        if(!inserted) throw new AlreadyExistsException("url", "tinyurl");
    }

    @Override
    public void deleteUrl(String hash) {
        session.execute(new StringBuilder("DELETE FROM tinyurl.url WHERE hash = '")
                .append(hash).append("' IF EXISTS;")
                .toString());
    }

    @Override
    public URL getURL(String hash) {
        URL url = null;

        ResultSet rs = session.execute(new StringBuilder("SELECT * FROM tinyurl.url WHERE hash = '")
                        .append(hash).append("';")
                        .toString());

        for(Row r : rs){
            url = new URL(r.getString("hash"),
                    r.getString("original_url"),
                    new Date(r.getDate("creation_date").getMillisSinceEpoch()),
                    new Date(r.getDate("expiration_date").getMillisSinceEpoch()),
                    r.getUUID("user_id"));
        }

        return url;
    }
}
