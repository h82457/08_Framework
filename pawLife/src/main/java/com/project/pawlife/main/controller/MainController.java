package com.project.pawlife.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.pawlife.main.model.service.MainService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final MainService service;

	

  @RequestMapping("/") public String mainPage() { return "common/main"; }
	 
}
