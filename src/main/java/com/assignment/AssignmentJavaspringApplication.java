package com.assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class AssignmentJavaspringApplication {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public static void main(String[] args) {
		SpringApplication.run(AssignmentJavaspringApplication.class, args);
	}

}
