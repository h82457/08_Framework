package com.project.pawlife.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.pawlife.member.model.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("member")
//@RequestMapping("member") <- 이게 있으면 모든 요청이 앞에 member가 있어야 함 안그럼 에러 남
@Slf4j
public class MemberController {

	private final MemberService service;


	 
	 /** 로그인 페이지 이동
		 * @return
		 */
		@GetMapping("login")
		public String loginPage() {
			return"member/login";
		}
		
		
		/** 회원 가입 페이지 이동
		 * @return
		 */
		@GetMapping("signup")
		public String signupPage() {
			
			return "member/signup";
		}
		
		
}
