package com.sti.bootcamp.dao;

import java.util.List;

import com.sti.bootcamp.exepcion.ExeptionCustome;
import com.sti.bootcamp.model.Account;
import com.sti.bootcamp.model.Transaction;

public interface TransactionDao {
	
	Transaction getById(int id) throws ExeptionCustome;
	Transaction save(Transaction transaction) throws ExeptionCustome;
	void delete(Transaction transaction) throws ExeptionCustome;
	
	List<Transaction> getList() throws ExeptionCustome;
	List<Transaction> getListByAccount(Account account) throws ExeptionCustome;
}
