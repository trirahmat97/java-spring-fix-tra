package com.sti.bootcamp.dao;

import java.util.List;

import com.sti.bootcamp.exepcion.ExeptionCustome;
import com.sti.bootcamp.model.Account;
import com.sti.bootcamp.model.Customer;

public interface AccountDao {
	Account getById(int id) throws ExeptionCustome;
	Account save(Account account) throws ExeptionCustome;
	void delete(Account account) throws ExeptionCustome;
	
	List<Account> getList() throws ExeptionCustome;
	List<Account> getListByCustomer(Customer customer) throws ExeptionCustome;
}
