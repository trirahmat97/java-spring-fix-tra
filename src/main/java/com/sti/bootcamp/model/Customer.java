package com.sti.bootcamp.model;

import javax.persistence.Column;
import javax.persistence.GenerationType;

import java.sql.Date;

import javax.persistence.*;

@Entity
public class Customer{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column
	private String username;
	@Column
	private String password;
	
	@Column(name="firsname")
	private String firsname;
	
	@Column
	private String lastname;
	@Column
	private Date birthdate;
	@Column
	private String phonetype;
	@Column
	private String phonenumber;
	
	
	
	public Customer() {	}
	
	public Customer(String username, String password, String firsname, String lastname, Date birthdate, String phonetype, String phonenumber) {
		this.username = username;
		this.password = password;
		this.firsname = firsname;
		this.lastname = lastname;
		this.birthdate = birthdate;
		this.phonetype = phonetype;
		this.phonenumber = phonenumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirsname() {
		return firsname;
	}

	public void setFirsname(String firsname) {
		this.firsname = firsname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getPhonetype() {
		return phonetype;
	}

	public void setPhonetype(String phonetype) {
		this.phonetype = phonetype;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	
		
}
