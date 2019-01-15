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
import com.sti.bootcamp.dao.TransactionDao;
import com.sti.bootcamp.exepcion.ExeptionCustome;
import com.sti.bootcamp.model.Account;
import com.sti.bootcamp.model.Transaction;
import com.sti.bootcamp.model.dto.CommonResponse;
import com.sti.bootcamp.model.dto.TransactionDto;

@RestController
@RequestMapping("/Transaction")
@SuppressWarnings("rawtypes")
public class TransactionController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionController.class);
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private TransactionDao transactionDao;
	
	@Autowired
	private AccountDao accountDao;
	
	@GetMapping("/transaction")
	public CommonResponse getById(@RequestParam(value="id", defaultValue="") String id) throws ExeptionCustome{
		LOGGER.info("id : {}", id);
		try {
			Transaction transaction = transactionDao.getById(Integer.parseInt(id));
			return new CommonResponse<TransactionDto>(modelMapper.map(transaction, TransactionDto.class));
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
	
	@PostMapping("/transaction")
	public CommonResponse insert(@RequestBody TransactionDto transactionDto) throws ExeptionCustome{
		try {
			Transaction transaction = modelMapper.map(transactionDto, Transaction.class);
			transaction.setId(0);
			transaction =  transactionDao.save(transaction);
			return new CommonResponse<TransactionDto>(modelMapper.map(transaction, TransactionDto.class));
		
		} catch (ExeptionCustome e) {
			return new CommonResponse("01", e.getMessage());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new CommonResponse("06", e.getMessage());
		}
	}
	
	@DeleteMapping("/transaction/{id}")
	public CommonResponse delet(@PathVariable ("id") Integer data) throws ExeptionCustome{
		try {
			Transaction checkTransaction = transactionDao.getById(data);
			if(checkTransaction == null) {
				return new CommonResponse("06", "customer tidak ditemukan");
			}
			transactionDao.delete(checkTransaction);
			return new CommonResponse();
		} catch (ExeptionCustome e) {
			return new CommonResponse("01", e.getMessage());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new CommonResponse("06", e.getMessage());
		}
	}
	
	@PutMapping("/transaction")
	public CommonResponse update(@RequestBody TransactionDto transaction) throws ExeptionCustome {
		try {
			Transaction checkTransaction = transactionDao.getById(transaction.getId());
			if(checkTransaction == null) {
				return new CommonResponse("14", "Account tidak ditemukan");
			}
			if(transaction.getType() != null) {
				checkTransaction.setType(transaction.getType());
			}
			if(transaction.getAmount() != 0) {
				checkTransaction.setAmount(transaction.getAmount());
			}
			if(transaction.getAmountsign() != null) {
				checkTransaction.setAmountsign(transaction.getAmountsign());
			}
			if(transaction.getAccount() != null)
			
				checkTransaction =  transactionDao.save(checkTransaction);
				return new CommonResponse<TransactionDto>(modelMapper.map(checkTransaction, TransactionDto.class));
		} catch (ExeptionCustome e) {
			return new CommonResponse("01", e.getMessage());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new CommonResponse("06", e.getMessage());
		}
	}
	
	@GetMapping("/transactions")
	public List<Transaction> getlist() throws ExeptionCustome{
		return transactionDao.getList();
	}
	
	@GetMapping(value="/list")
	public CommonResponse getlist(@RequestParam(name="accountid", defaultValue="") String id) throws ExeptionCustome{
		try {
			List<Transaction> transactions;
			if (!StringUtils.isEmpty(id)) {
				Account checkAccount = accountDao.getById(Integer.parseInt(id));
				if (checkAccount == null) {
					throw new ExeptionCustome("Account tidak ditemukan !");
				}
				
				transactions = transactionDao.getListByAccount(checkAccount);
				
			} else {
				LOGGER.info("account get list, param : {} ", id);
				transactions= transactionDao.getList();
			}
			
			return new CommonResponse<List<TransactionDto>>(transactions.stream().map(acc -> modelMapper.map(acc,TransactionDto.class)).collect(Collectors.toList()));
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
