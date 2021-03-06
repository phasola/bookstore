package com.bk.service

import spock.lang.Specification

import com.bk.model.Customer
import com.bk.repository.CustomerRepository

/**
 * @author Andrei Petraru
 * 14 Jun 2013
 */
class CustomerServiceTest extends Specification {
	CustomerRepository repository = Mock()
	CustomerService service = new CustomerService()

	def setup() {
		service.@repository = repository
	}

	def 'test save'() {
		setup:
		Customer customer = new Customer(username: 'user', password: 'pass', emailAddress: 'email')
		
		when:
		service.save(customer)
		
		then:
		1 * repository.save(customer)
	}
	def 'find by ID'() {
		when:
		service.findById(1L)
		
		then:
		1 * repository.findById(1L)
	}
	def 'find by email address'() {
		setup:
		String email = 'mail@mail.com'
		
		when:
		service.findByEmailAddress(email)
		
		then:
		1 * repository.findByEmailAddress(email)
	}
	def 'find by username'() {
		setup:
		String username = 'user'
		
		when:
		service.findByUsername(username)
		
		then:
		1 * repository.findByUsername(username)
	}
}
