package com.kh.test.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.test.user.model.dto.User;
import com.kh.test.user.model.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

	private final UserService service;
	
	@GetMapping("searchSuccess")
	public String searchSuccess(@RequestParam("userId") String userId, Model model) {
		
		User user = service.searchId(userId);
		
		String path = "";
		
		if(user != null) {
			
			path = "searchSuccess";
			model.addAttribute("user", user);
			
		} else path = "searchFail";
			
		return path;
	}
}
