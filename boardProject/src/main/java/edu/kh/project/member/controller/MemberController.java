package edu.kh.project.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.member.model.service.MemberService;
import lombok.extern.slf4j.Slf4j;

@SessionAttributes({"loginMember"}) // model에 추가된 속성(Attribute)중 key 값이 일치하는 속성을 session scope로 변경
@Controller
@RequestMapping("member")
@Slf4j
public class MemberController {

	@Autowired // 의존성 주입(DI)
	private MemberService service;
	
	
	/* [로그인]
	 *   ㄴ 특정 사이트에 ID/PW 등을 입력, 해당 정보가 있으면 조회/서비스 이용
	 *   ㄴ 로그인한 정보를 session에 기록, 로그아웃/브라우저 종료시까지 해당 정보를 계속 이용 가능하게 함
	 */
	
	
	/**
	 * @param inputMember : 커맨드 객체 (@ModelAttribute 생략), memberEmail, memberPw가 세팅된 상태
	 *						ㄴ> 요청시 전달 받은 파라미터를 같은 이름의 필드에 세팅한 객체
	 *
	 * @param ra : 리다이렉트시 request scope로 데이터 전달하는 객체
	 * 
	 * @param model : 데이터 전달용 객체 (request scope)
	 * 
	 * @return : "redirect:/"
	 */
	@PostMapping("login")
	public String login(Member inputMember, RedirectAttributes ra, Model model) {
		
		// 로그인 서비스 호출
		Member loginMember = service.login(inputMember);
		
		// 로그인 실패 ~> redirect
		if(loginMember == null) {
			ra.addFlashAttribute("message", "아이디 또는 비밀번호가 일치하지 않습니다.");
		}
		
		// 로그인 성공 ~> session scope에 loginMember 추가
		if(loginMember != null) {
			
			model.addAttribute("loginMember", loginMember);
			
			// 1) request scope에 세팅
			
			// 2) 클래스 위에 @SessionAttributes() 어노테이션때문에 session 
			
		}
		
		
		return "redirect:/";
	}
	
	
	
}
