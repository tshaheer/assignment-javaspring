package com.assignment.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.assignment.utils.TestUtil;

public class AccountTests {

	@Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Account.class);
        Account account1 = new Account();
        account1.setId(1L);
        Account account2 = new Account();
        account2.setId(account1.getId());
        assertThat(account1).isEqualTo(account2);
        account2.setId(2L);
        assertThat(account1).isNotEqualTo(account2);
        account1.setId(null);
        assertThat(account1).isNotEqualTo(account2);
    }
}
