package com.priyanka.tinyurl.database.cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.priyanka.tinyurl.database.DatabaseConnector;
import org.springframework.stereotype.Component;

@Component
public class CassandraConnector implements DatabaseConnector {

    private Cluster cluster;
    private Session session;

    @Override
    public void connect(){
        if(cluster == null) {
            Cluster.Builder b = Cluster.builder().addContactPoint("127.0.0.1");
            b.withPort(9042);
            cluster = b.build();
            session = cluster.connect();
        }
    }

    @Override
    public Session getSession(){
        return this.session;
    }

    @Override
    public void close(){
        session.close();
        cluster.close();
    }

}
