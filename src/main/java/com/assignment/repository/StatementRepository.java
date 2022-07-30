package com.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.assignment.domain.Statement;

/**
 * Spring Data SQL repository for the Statement entity.
 * 
 * @author Shaheer
 * @since 30 July 2022
 */
@Repository
public interface StatementRepository extends JpaRepository<Statement, Long> {

}
