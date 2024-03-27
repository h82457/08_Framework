package edu.kh.project.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	
	@RequestMapping("/") // "/' 요청 매핑 (method 가리지 X)
	public String mainPage() {
		
		return "common/main"; //  메인 페이지로 forward
	}
}
