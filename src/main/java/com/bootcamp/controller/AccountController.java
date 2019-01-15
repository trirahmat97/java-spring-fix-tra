package com.bootcamp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sti.bootcamp.dao.AccountDao;
import com.sti.bootcamp.dao.CustomerDao;
import com.sti.bootcamp.dao.TransactionDao;
import com.sti.bootcamp.exepcion.ExeptionCustome;
import com.sti.bootcamp.model.Account;
import com.sti.bootcamp.model.Customer;
import com.sti.bootcamp.model.Transaction;
import com.sti.bootcamp.model.dto.AcccountDto;
import com.sti.bootcamp.model.dto.CommonResponse;
import com.sti.bootcamp.model.dto.TransactionDto;


@RestController
@RequestMapping("/Account")
@SuppressWarnings("rawtypes")
public class AccountController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private TransactionDao transactionDao;
	
	@GetMapping("/account")
	public CommonResponse getById(@RequestParam(value="id", defaultValue="") String id) throws ExeptionCustome {
		LOGGER.info("id : {}", id);
		try {
			Account account = accountDao.getById(Integer.parseInt(id));
			return new CommonResponse<AcccountDto>(modelMapper.map(account, AcccountDto.class));
		} catch (ExeptionCustome e) {
			LOGGER.error(e.getMessage());
			return new CommonResponse("01", e.getMessage());
		} catch (NumberFormatException e) {
			LOGGER.error(e.getMessage());
			return new CommonResponse("06", "parameter harus angka");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new CommonResponse("06", e.getMessage());
		}
	}
	
	@GetMapping("/account/{id}/transaksis")
	public CommonResponse getTransaksi(@PathVariable("id") String id) throws ExeptionCustome {
		LOGGER.info("id : {}", id);
		try {
			Account account = accountDao.getById(Integer.parseInt(id));
			List<Transaction> listTransaction = transactionDao.getListByAccount(account);
			return new CommonResponse<List<TransactionDto>>(listTransaction.stream().map(acc -> modelMapper.map(acc,TransactionDto.class)).collect(Collectors.toList()));
		} catch (ExeptionCustome e) {
			LOGGER.error(e.getMessage());
			return new CommonResponse("01", e.getMessage());
		} catch (NumberFormatException e) {
			LOGGER.error(e.getMessage());
			return new CommonResponse("06", "parameter harus angka");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new CommonResponse("06", e.getMessage());
		}
	}
	
	@PostMapping("/account")
	public CommonResponse insert(@RequestBody AcccountDto accountDto) throws ExeptionCustome{
		try {
			Account account = modelMapper.map(accountDto, Account.class);
			account.setId(0);
			account =  accountDao.save(account);
			return new CommonResponse<AcccountDto>(modelMapper.map(account, AcccountDto.class));
		} catch (ExeptionCustome e) {
			return new CommonResponse("01", e.getMessage());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new CommonResponse("06", e.getMessage());
		}
	}
	
	@DeleteMapping("/account/{id}")
	public CommonResponse delete(@PathVariable ("id") Integer data) throws ExeptionCustome{
		try {
			Account checkAccount = accountDao.getById(data);
			if(checkAccount == null) {
				return new CommonResponse("06", "customer tidak ditemukan");
			}
			accountDao.delete(checkAccount);
			return new CommonResponse();
		} catch (ExeptionCustome e) {
			return new CommonResponse("01", e.getMessage());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new CommonResponse("06", e.getMessage());
		}
	}
	
	@PutMapping("/account")
	public CommonResponse update(@RequestBody AcccountDto account) throws ExeptionCustome {
		try {
			Account checkAccount = accountDao.getById(account.getId());
			if(checkAccount == null) {
				return new CommonResponse("14", "Account tidak ditemukan");
			}
			if(account.getBalance() != 0) {
				checkAccount.setBalance(account.getBalance());
			}
			if(account.getOpendate()!= null) {
				checkAccount.setOpendate(account.getOpendate());
			}
			if(account.getCustomer() != null) {
				checkAccount.setCustomer(account.getCustomer());
			}
			checkAccount =  accountDao.save(checkAccount);
			return new CommonResponse<AcccountDto>(modelMapper.map(checkAccount, AcccountDto.class));
		} catch (ExeptionCustome e) {
			return new CommonResponse("01", e.getMessage());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new CommonResponse("06", e.getMessage());
		}
	}
	
	@GetMapping("/accounts")
	public List<Account> getlist() throws ExeptionCustome{
		return accountDao.getList();
	}
	
	@GetMapping(value="/list")
	public  CommonResponse getlist(@RequestParam(name="customerid", defaultValue="") String account) throws ExeptionCustome{
		try {
			List<Account> accounts;
			if (!StringUtils.isEmpty(account)) {
				Customer checkCustomer = customerDao.getById(Integer.parseInt(account));
				if (checkCustomer == null) {
					throw new ExeptionCustome("Customer tidak ditemukan !");
				}
				
				accounts = accountDao.getListByCustomer(checkCustomer);
				
			} else {
				LOGGER.info("account get list, param : {} ", account);
				accounts = accountDao.getList();
			}
			
			return new CommonResponse<List<AcccountDto>>(accounts.stream().map(acc -> modelMapper.map(acc,AcccountDto.class)).collect(Collectors.toList()));
		
		} catch (ExeptionCustome e) {
			throw e;
		} catch(NumberFormatException e) {
			return new CommonResponse("01", e.getMessage());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new CommonResponse("06", e.getMessage());
		}
	}

}
