package com.sti.bootcamp.model.dto;

import java.sql.Date;


import com.sti.bootcamp.model.Customer;

public class AcccountDto {
	
	private int id;
	private Date opendate;
	private int balance;
	private Customer customer;
	
	public AcccountDto() {}
	
	public AcccountDto(Date opendate, int balance, Customer customer) {
		this.opendate = opendate;
		this.balance = balance;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getOpendate() {
		return opendate;
	}
	public void setOpendate(Date opendate) {
		this.opendate = opendate;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	
}
