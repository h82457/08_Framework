package com.kh.test.customer.model.service;

import org.springframework.stereotype.Service;

import com.kh.test.customer.model.dto.Customer;
import com.kh.test.customer.model.mapper.CustomerMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomeServiceImpl implements CustomerService{

	private final CustomerMapper mapper;

	// 고객 추가
	@Override
	public int insertCustomer(Customer inputCustomer) { return mapper.insertCustomer(inputCustomer); }
}
