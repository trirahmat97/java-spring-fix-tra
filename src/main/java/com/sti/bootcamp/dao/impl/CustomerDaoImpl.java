package com.sti.bootcamp.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.beans.factory.annotation.Autowired;

import com.sti.bootcamp.dao.CustomerDao;
import com.sti.bootcamp.dao.repository.CustomerRepository;
import com.sti.bootcamp.exepcion.ExeptionCustome;
import com.sti.bootcamp.model.Customer;

public class CustomerDaoImpl extends BaseImpl implements CustomerDao {
	
	@Autowired
	private CustomerRepository repository;
	
	@Override
	public Customer getById(int id) throws ExeptionCustome {
		return repository.findOne(id);
	}

	@Override
	public Customer save(Customer customer) throws ExeptionCustome {
		return repository.save(customer);
	}

	@Override
	public void delete(Customer customer) throws ExeptionCustome {
		repository.delete(customer);
	}

	@Override
	public List<Customer> getList() throws ExeptionCustome {
		CriteriaBuilder critB = em.getCriteriaBuilder();
		CriteriaQuery<Customer> query = critB.createQuery(Customer.class);
		query.from(Customer.class);
		TypedQuery<Customer> q = em.createQuery(query);
		return q.getResultList();
	}
	
}
