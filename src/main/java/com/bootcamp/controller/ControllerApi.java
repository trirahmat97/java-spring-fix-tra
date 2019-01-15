package com.bootcamp.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.sti.bootcamp.exepcion.ExeptionCustome;
import com.sti.bootcamp.model.Account;
import com.sti.bootcamp.model.Customer;
import com.sti.bootcamp.model.dto.AcccountDto;
import com.sti.bootcamp.model.dto.CommonResponse;
import com.sti.bootcamp.model.dto.CustomerDto;

@RestController
@RequestMapping("/Customer")
@SuppressWarnings("rawtypes")
public class ControllerApi {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerApi.class);
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private AccountDao accountDao;
	
	@GetMapping("/customer/{id}")
	public CommonResponse getById(@PathVariable("id") String id) throws ExeptionCustome {
		LOGGER.info("id : {}", id);
		try {
			Customer customer = customerDao.getById(Integer.parseInt(id));
			return new CommonResponse<CustomerDto>(modelMapper.map(customer, CustomerDto.class));
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
	
	@GetMapping("/customer/{id}/accounts")
	public CommonResponse getAccounts(@PathVariable("id") String id) throws ExeptionCustome {
		LOGGER.info("id : {}", id);
		try {
			Customer customer = customerDao.getById(Integer.parseInt(id));
			List<Account> listaccount = accountDao.getListByCustomer(customer);
			return new CommonResponse<List<AcccountDto>>(listaccount.stream().map(cust -> modelMapper.map(cust, AcccountDto.class)).collect(Collectors.toList()));
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
	
	@PostMapping("/customer")
	public CommonResponse insert(@RequestBody CustomerDto customerDto) throws ExeptionCustome {
		try {
			Customer customer = modelMapper.map(customerDto, Customer.class);
			customer.setId(0);
			customer =  customerDao.save(customer);
			
			return new CommonResponse<CustomerDto>(modelMapper.map(customer, CustomerDto.class));
		} catch (ExeptionCustome e) {
			return new CommonResponse("01", e.getMessage());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new CommonResponse("06", e.getMessage());
		}
	}
	
	@DeleteMapping("/customer/{id}")
	public CommonResponse delete(@PathVariable("id") Integer data) throws ExeptionCustome {
		try {
			Customer checkCustomer = customerDao.getById(data);
			if(checkCustomer == null) {
				return new CommonResponse("06", "customer tidak ditemukan");
			}
			customerDao.delete(checkCustomer);
			return new CommonResponse();
		} catch (ExeptionCustome e) {
			return new CommonResponse("01", e.getMessage());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new CommonResponse("06", e.getMessage());
		}
	}
	
	@PutMapping("/customer")	
	public CommonResponse update(@RequestBody CustomerDto customer) throws ExeptionCustome {
		try {
			Customer checkCustomer = customerDao.getById(customer.getId());
			if(checkCustomer == null) {
				return new CommonResponse("14", "customer tidak ditemukan");
			}
			
			if(customer.getUsername() != null) {
				checkCustomer.setUsername(customer.getUsername());
			}

			if(customer.getBirthdate()!=null) {
				checkCustomer.setBirthdate(customer.getBirthdate());
			}
			if(customer.getFirsname()!=null) {
				checkCustomer.setFirsname(customer.getFirsname());
			}
			if(customer.getLastname()!=null) {
				checkCustomer.setLastname(customer.getLastname());
			}
			if(customer.getPhonenumber()!=null) {
				checkCustomer.setPhonenumber(customer.getPhonenumber());
			}
			if(customer.getPhonetype()!=null) {
				checkCustomer.setPhonetype(customer.getPhonetype());
			}
			checkCustomer =  customerDao.save(checkCustomer);
			return new CommonResponse<CustomerDto>(modelMapper.map(checkCustomer, CustomerDto.class));
		} catch (ExeptionCustome e) {
			return new CommonResponse("01", e.getMessage());
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return new CommonResponse("06", e.getMessage());
		}
	}
	
	@GetMapping("/list")
	public CommonResponse getlist(@RequestParam(name="customer", defaultValue="") String customer) throws ExeptionCustome{
		try {
			LOGGER.info("customer get list, params : {}", customer);
			List<Customer> customers = customerDao.getList();
			return new CommonResponse<List<CustomerDto>>(customers.stream().map(cust -> modelMapper.map(cust, CustomerDto.class)).collect(Collectors.toList()));
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
