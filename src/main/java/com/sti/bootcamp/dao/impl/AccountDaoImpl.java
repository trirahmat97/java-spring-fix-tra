package com.sti.bootcamp.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.beans.factory.annotation.Autowired;

import com.sti.bootcamp.dao.AccountDao;
import com.sti.bootcamp.dao.repository.AccountRepository;
import com.sti.bootcamp.exepcion.ExeptionCustome;
import com.sti.bootcamp.model.Account;
import com.sti.bootcamp.model.Customer;

public class AccountDaoImpl extends BaseImpl implements AccountDao {
	
	@Autowired
	private AccountRepository repository;
	
	@Override
	public Account getById(int id) throws ExeptionCustome {
		return repository.findOne(id);
	}

	@Override
	public Account save(Account account) throws ExeptionCustome {
		return repository.save(account);
	}

	@Override
	public void delete(Account account) throws ExeptionCustome {
		repository.delete(account);
	}

	@Override
	public List<Account> getList() throws ExeptionCustome {
		CriteriaBuilder critB = em.getCriteriaBuilder();
		CriteriaQuery<Account> query = critB.createQuery(Account.class);
		query.from(Account.class);
		TypedQuery<Account> q = em.createQuery(query);
		return q.getResultList();
	}

	@Override
	public List<Account> getListByCustomer(Customer customer) throws ExeptionCustome {
		return repository.findByCustomer(customer);
	}

}
