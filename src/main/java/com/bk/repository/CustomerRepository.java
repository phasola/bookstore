package com.bk.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bk.model.Customer;

/**
 * @author Andrei Petraru
 */

@Transactional(readOnly = true)
public interface CustomerRepository extends Repository<Customer, Long>, QueryDslPredicateExecutor<Customer> {

    Customer findById(Long id);

    @Transactional
    Customer save(Customer customer);

    Customer findByEmailAddress(String emailAddress);

    Customer findByUsername(String username);

}
