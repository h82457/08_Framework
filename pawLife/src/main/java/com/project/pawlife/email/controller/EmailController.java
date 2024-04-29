package com.project.pawlife.email.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.project.pawlife.email.model.service.EmailService;

import lombok.RequiredArgsConstructor;
import oracle.jdbc.proxy.annotation.Post;

@SessionAttributes({"authKey"})
@Controller
@RequiredArgsConstructor
@RequestMapping("email")
public class EmailController {
	
	private final EmailService service;
	
	/** 이메일 발송
	 * @param email
	 * @param model
	 * @return
	 */
	@ResponseBody
	@PostMapping("sendEmail")
	public int sendEmail(@RequestBody String email, Model model) { // model로 session에 올려줌

		String authKey = service.sendEmail("sendEmail", email);
		
		if(authKey != null) {
			return 1;
		}
		
		return 0;
	}
	
	/** 인증번호 비교 (입력값, session값)
	 * @param map
	 * @return
	 */
	@ResponseBody
	@PostMapping("checkAuthKey")
	public int checkAuthKey(@RequestBody Map<String, Object> map) {
		
		return service.checkAuthKey(map);
	}
	

}
