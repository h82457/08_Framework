package edu.kh.project.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.main.service.MainService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
@RequiredArgsConstructor
public class MainController {
	
	private final MainService service;
	
	@RequestMapping("/") // "/' 요청 매핑 (method 가리지 X)
	public String mainPage() {
		
		return "common/main"; //  메인 페이지로 forward
	}
	
	/** 비밀번호 초기화
	 * @param inputNo : 초기화 할 회원 번호
	 * @return result
	 */
	@ResponseBody
	@PutMapping("resetPw")
	public int resetPw(@RequestBody int inputNo) {
		
		
		return service.resetPw(inputNo);
	}

	/** 비밀번호 초기화
	 * @param inputNo
	 * @return result
	 */
	@ResponseBody
	@PutMapping("restoration")
	public int restoration(@RequestBody int inputNo) { return service.restoration(inputNo); }

	
	@GetMapping("loginError")
	public String loginError(RedirectAttributes ra) {
		 // LoginFilter -> loginError로 리다이렉트-> message를 만들어서 메인페이지로 리다이렉트
		ra.addFlashAttribute("message", "로그인 후 이용해주세요");
		
		return "redirect:/";
	}
	
	
}

