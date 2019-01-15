package com.sti.bootcamp.dao.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sti.bootcamp.model.Account;
import com.sti.bootcamp.model.Customer;

public interface AccountRepository extends PagingAndSortingRepository<Account, Integer>{
	Account findById(String id);
	List<Account> findByCustomer(Customer customer);
}
