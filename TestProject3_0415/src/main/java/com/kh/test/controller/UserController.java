package com.kh.test.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.kh.test.model.dto.User;
import com.kh.test.model.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class UserController {

	private final UserService service;
	
	@GetMapping("search")
	public String searchUser(@RequestParam("inputNo") String inputNo, Model model) {
		
		User user = service.searchUser(inputNo);
		
		String path = "";
		
		if(user != null) {
			path = "searchSuccess";
			model.addAttribute(user);
		}
		else {
			path = "searchFail";
					
		}
		
			
		
		return "redirect:/" + path;
	}
	
}
