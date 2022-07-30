package com.assignment.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.assignment.domain.Account;

/**
 * Spring Data SQL repository for the Account entity.
 * 
 * @author Shaheer
 * @since 30 July 2022
 */
@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

}
