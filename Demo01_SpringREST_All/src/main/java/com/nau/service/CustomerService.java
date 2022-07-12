package com.nau.service;

import java.util.List;

import com.nau.dto.CustomerDTO;
import com.nau.exception.InfyBankException;

public interface CustomerService {
	public Integer addCustomer(CustomerDTO customerDTO) throws InfyBankException;
	public CustomerDTO getCustomer(Integer customerId) throws InfyBankException;
	public void updateCustomer(Integer customerId, String emailId)throws InfyBankException;
	public void deleteCustomer(Integer customerId)throws InfyBankException;
	public List<CustomerDTO> getAllCustomers() throws InfyBankException;
}
