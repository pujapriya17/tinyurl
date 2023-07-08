package com.priyanka.tinyurl;

import com.priyanka.tinyurl.database.DatabaseSetup;
import com.priyanka.tinyurl.database.cassandra.CassandraSetup;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class TinyurlApplication{

	public static void main(String[] args) {
		SpringApplication.run(TinyurlApplication.class, args);

		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		applicationContext.scan("com.priyanka.tinyurl.database");
		applicationContext.refresh();

		DatabaseSetup dbSetup = applicationContext.getBean(CassandraSetup.class);
		try {
			dbSetup.setup();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
