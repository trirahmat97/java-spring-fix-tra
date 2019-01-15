package com.sti.bootcamp.dao;

import java.util.List;

import com.sti.bootcamp.exepcion.ExeptionCustome;
import com.sti.bootcamp.model.Customer;

public interface CustomerDao {
	Customer getById(int id) throws ExeptionCustome;
	Customer save(Customer customer) throws ExeptionCustome;
	void delete(Customer customer) throws ExeptionCustome;
	
	List<Customer> getList() throws ExeptionCustome;
}
