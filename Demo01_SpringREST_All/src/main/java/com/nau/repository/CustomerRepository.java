package com.nau.repository;

import org.springframework.data.repository.CrudRepository;

import com.nau.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {
}
