package com.kh.test.customer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import com.kh.test.customer.model.dto.Customer;
import com.kh.test.customer.model.service.CustomerService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
public class CustomerController {

	private final CustomerService service;
	
	/** 고객 추가 
	 * @param inputCustomer
	 * @return
	 */
	@PostMapping("addCustomer")
	public String insertCustomer(Customer inputCustomer, Model model) {

		int result = service.insertCustomer(inputCustomer);
		
		String message = "";
		String path = "";
		
		
		if(result > 0) {
			message = inputCustomer.getCustomerName() + " 고객님 추가 성공!!!";
			path = "result";
			model.addAttribute("message", message);
		}
		
		return path;
	}
}
