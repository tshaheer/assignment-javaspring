package com.assignment.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import com.assignment.utils.TestUtil;

class StatementTests {

	@Test
	void equalsVerifier() throws Exception {
		TestUtil.equalsVerifier(Statement.class);
		Statement statement1 = new Statement();
		statement1.setId(1L);
		Statement statement2 = new Statement();
		statement2.setId(statement1.getId());
		statement2.setDate(LocalDate.now());
		statement2.setAccount(new Account());
		statement2.setAmount(new BigDecimal(1));
		assertThat(statement1).isEqualTo(statement2);
		statement2.setId(2L);
		assertThat(statement1).isNotEqualTo(statement2);
		statement1.setId(null);
		assertThat(statement1).isNotEqualTo(statement2);
	}

}
