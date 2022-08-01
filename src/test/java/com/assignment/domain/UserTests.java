package com.assignment.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.assignment.utils.TestUtil;

class UserTests {

	@Test
	void equalsVerifier() throws Exception {
		TestUtil.equalsVerifier(User.class);
		User user1 = new User();
		user1.setId(1L);
		User user2 = new User();
		user2.setId(user1.getId());
		assertThat(user1).isEqualTo(user2);
		user2.setId(2L);
		assertThat(user1).isNotEqualTo(user2);
		user1.setId(null);
		assertThat(user1).isNotEqualTo(user2);
	}
}
