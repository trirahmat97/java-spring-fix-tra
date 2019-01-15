package com.sti.bootcamp.dao.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sti.bootcamp.model.Customer;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer> {
	Customer findById(int id);
}
