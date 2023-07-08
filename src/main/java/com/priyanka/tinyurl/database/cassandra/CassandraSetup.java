package com.priyanka.tinyurl.database.cassandra;

import com.datastax.driver.core.Session;
import com.priyanka.tinyurl.database.DatabaseSetup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class CassandraSetup implements DatabaseSetup {

    @Autowired
    private CassandraConnector client;
    private Session session;

    @Override
    public void setup(){
        client.connect();

        this.session = client.getSession();
        createKeySpace("tinyurl", "SimpleStrategy", 1);
        createTables();

        client.close();
    }

    public void createKeySpace(String keyspaceName, String replicationStrategy, int replicationFactor){
        StringBuilder sb = new StringBuilder("CREATE KEYSPACE IF NOT EXISTS ")
                .append(keyspaceName).append(" WITH replication = {")
                .append("'class': '").append(replicationStrategy)
                .append("','replication_factor': ").append(replicationFactor)
                .append("};");

        String query = sb.toString();
        session.execute(query);
    }

    public void createTables() {
        File databaseDir = new File(getClass().getClassLoader().getResource("database/cassandra").getFile());

        for(File databaseFile : databaseDir.listFiles()) {
            InputStream fis = null;
            try {
                fis = new FileInputStream(databaseFile);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            String query = null;
            try {
                query = new String(fis.readAllBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            session.execute(query);
        }
    }

}
