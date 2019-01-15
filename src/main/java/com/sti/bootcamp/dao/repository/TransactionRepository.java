package com.sti.bootcamp.dao.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.sti.bootcamp.model.Account;
import com.sti.bootcamp.model.Transaction;

public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Integer> {
	Transaction findById(String id);
	List<Transaction> findByAccount(Account account);
}
