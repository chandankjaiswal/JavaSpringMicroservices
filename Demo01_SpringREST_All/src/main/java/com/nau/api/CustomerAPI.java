package com.nau.api;


import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nau.dto.CustomerDTO;
import com.nau.exception.InfyBankException;
import com.nau.service.CustomerService;

@RestController
@RequestMapping(value = "/infybank")
@Validated
public class CustomerAPI {
	@Autowired
	private CustomerService customerService;
	@Autowired
	private Environment environment;

	@GetMapping(value = "/customers")
	public ResponseEntity<List<CustomerDTO>> getAllCustomers() throws InfyBankException {
		List<CustomerDTO> customerDTOs = customerService.getAllCustomers();
		return new ResponseEntity<>(customerDTOs, HttpStatus.OK);
	}

	@GetMapping(value = "/customers/{customerId}",produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.ALL_VALUE})
	public ResponseEntity<CustomerDTO> getCustomer(
			@PathVariable 
			@Min(value = 1, message = "Customer id should be between 1 and 100") 
			@Max(value = 100, message = "Customer id should be between 1 and 100") 
			Integer customerId)
			throws InfyBankException {
		CustomerDTO customerDTO = customerService.getCustomer(customerId);
		return new ResponseEntity<>(customerDTO, HttpStatus.OK);
	}

	@PostMapping(value = "/customers")
	public ResponseEntity<String> addCustomer(@Valid @RequestBody CustomerDTO customerDTO) throws InfyBankException {
		Integer customerId = customerService.addCustomer(customerDTO);
		String successMessage = environment.getProperty("API.INSERT_SUCCESS") + customerId;
		return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
	}

	@PutMapping(value = "/customers/{customerId}")
	public ResponseEntity<String> updateCustomer(@PathVariable Integer customerId, @RequestBody CustomerDTO customer)
			throws InfyBankException {
		customerService.updateCustomer(customerId, customer.getEmailId());
		String successMessage = environment.getProperty("API.UPDATE_SUCCESS");
		return new ResponseEntity<>(successMessage, HttpStatus.OK);
	}

	@DeleteMapping(value = "/customers/{customerId}")
	public ResponseEntity<String> deleteCustomer(@PathVariable Integer customerId) throws InfyBankException {
		customerService.deleteCustomer(customerId);
		String successMessage = environment.getProperty("API.DELETE_SUCCESS");
		return new ResponseEntity<>(successMessage, HttpStatus.OK);
	}
}
