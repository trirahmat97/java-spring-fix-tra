package com.sti.bootcamp.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.beans.factory.annotation.Autowired;

import com.sti.bootcamp.dao.TransactionDao;
import com.sti.bootcamp.dao.repository.TransactionRepository;
import com.sti.bootcamp.exepcion.ExeptionCustome;
import com.sti.bootcamp.model.Account;
import com.sti.bootcamp.model.Transaction;

public class TransactionDaoImpl extends BaseImpl implements TransactionDao{

	@Autowired
	private TransactionRepository repository;
	
	@Override
	public Transaction getById(int id) throws ExeptionCustome {
		return repository.findOne(id);
	}

	@Override
	public Transaction save(Transaction transaction) throws ExeptionCustome {
		return repository.save(transaction);
	}

	@Override
	public void delete(Transaction transaction) throws ExeptionCustome {
		repository.delete(transaction);
		
	}

	@Override
	public List<Transaction> getList() throws ExeptionCustome {
		CriteriaBuilder critB = em.getCriteriaBuilder();
		CriteriaQuery<Transaction> query = critB.createQuery(Transaction.class);
		query.from(Transaction.class);
		TypedQuery<Transaction> q = em.createQuery(query);
		return q.getResultList();
	}

	@Override
	public List<Transaction> getListByAccount(Account account) throws ExeptionCustome {
		return repository.findByAccount(account);
	}


}
