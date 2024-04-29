package com.project.pawlife.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.pawlife.member.model.dto.Member;
import com.project.pawlife.member.model.service.MemberService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SessionAttributes({"loginMember"}) // request로 올라간 loginMember session에 올리기
@Controller
@RequiredArgsConstructor
@RequestMapping("member")
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
		
	
		/** 회원가입
		 * @param inputMember
		 * @param ra
		 * @return
		 */
		@PostMapping("signup")
		   public String signup(Member inputMember, RedirectAttributes ra) {
			 
			  int result = service.signup(inputMember);
			  
			  // 가입 성공하면 메인으로 보내고 실패하면 회원 가입 페이지로 redirect
			  String path = null;
			  String message = null;
			  
			  if(result >0 ) { 
				  message = inputMember.getMemberNickname() + "님의 가입 환영합니다.";
				  path= "/";
			  }else {
				  message = "회원 가입을 실패했습니다";
				  path= "/signup";
			  }
			  
			  ra.addFlashAttribute("message", message);
			  return "redirect:" + path;
		}
		
		/** 이메일 중복 검사
		 * @param memberEmail
		 * @return
		 */
		@ResponseBody
		@GetMapping("emailCheck")
		public int emailCheck(@RequestParam String memberEmail) {
			
			return service.emailCheck(memberEmail);
		}
		
		/** 닉네임 중복 검사
		 * @param memberNickname
		 * @return
		 */
		@ResponseBody
		@GetMapping("checkNickname")
		public int checkNickname(@RequestParam String memberNickname) {
			
			return service.checkNickname(memberNickname);
		}
		
		/** 전화번호 중복 검사
		 * @param memberNickname
		 * @return
		 */
		@ResponseBody
		@GetMapping("checkTel")
		public int checkTel(@RequestParam String memberTel) {
			
			return service.checkTel(memberTel);
		}
		

		/** 로그인
		 * @param inputMember
		 * @param ra
		 * @param model
		 * @param saveId
		 * @param resp
		 * @return
		 */
		@PostMapping("login")
		public String login(Member inputMember, 
				                    RedirectAttributes ra,
				                    Model model,
				                    @RequestParam(value="saveId", required=false) String saveId,
				                    HttpServletResponse resp
				                    ) {
			
		
			// 체크박스에 value가 없을 때
			// - 체크가 된 경우 : "on" (null 아님)
			// - 체크가  안된 경우 : null
			//log.debug("saveId: "+saveId);
			
			// 로그인 서비스 호출
			Member loginMember = service.login(inputMember);
			
			// 로그인 실패 시 
			if(loginMember == null) {
				ra.addFlashAttribute("message","아이디 또는 비밀번호가 일치하지 않습니다");
				return "redirect:/member/login";
			}
		
			// 로그인 성공
			if(loginMember !=null) {
				ra.addFlashAttribute("message","로그인 성공");
				model.addAttribute("loginMember", loginMember);
				
				 // 아이디 저장 (Cookie)
				 // 쿠키 객체 생성 (K:V)
				Cookie cookie = new Cookie("saveId", loginMember.getMemberEmail());
				
				//어떤 요청을 할 때 쿠키가 첨부될지 지정
				cookie.setPath("/");
			
				// 쿠키 만료 기간
				if(saveId !=null) { // 아이디 저장 체크 시 
					cookie.setMaxAge(30*24*60*60); // 초 단위로 지정
				}else {// 미체크 시
					cookie.setMaxAge(0); // 0초 (클라이언트 쿠키 삭제)
				}
				// 응답 객체에 쿠키 추가하여 전달
				resp.addCookie(cookie);
			} // -> cookie 부분 영상 보고 화면에서 설정하기
			
			return "redirect:/"; // 메인 페이지 재요청
		}
		
		
		
		/** 로그아웃
		 * @param status
		 * @return "redirect:/"
		 */
		@GetMapping("logout")
         public String logout(SessionStatus status) {
			
			status.setComplete(); // @SessionAttribute로 등록된 세션을 완료 시킴
			
			return "redirect:/"; 
		}
		
		/** 빠른 로그인 (나중에 지우기)
		 * @param memberEmail
		 * @param model
		 * @param ra
		 * @return
		 */
		@GetMapping("quickLogin")
		public String quickLogin(
				@RequestParam("memberEmail") String memberEmail,
				Model model,
				RedirectAttributes ra) {
			
			Member loginMember = service.quickLogin(memberEmail);
			
			if(loginMember == null) {
				ra.addFlashAttribute("message","해당 회원은 존재하지 않습니다");
			}
			if( loginMember !=null) {
				model.addAttribute("loginMember",loginMember);
			}
			
			return "redirect:/";
		}




}