package com.assignment;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.assignment.service.StatementService;

@SpringBootTest
class AssignmentJavaspringApplicationTests {
	
	@Autowired
	private StatementService statementService;

	@Test
	void contextLoads() {
		assertThat(statementService).isNotNull();
	}
}
