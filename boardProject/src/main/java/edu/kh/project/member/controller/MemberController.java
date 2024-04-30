package edu.kh.project.member.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.member.model.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
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
	 * @param saveId : 아이디 저장 체크 여부
	 * 
	 * @param  resp : 쿠키 생성, 추가를 위해 얻어온 객체
	 * 
	 * @return : "redirect:/"
	 */
	@PostMapping("login")
	public String login(Member inputMember,
			RedirectAttributes ra,
			Model model,
			@RequestParam(value = "saveId", required = false) String saveId,
			HttpServletResponse resp) {
		
		// 체크박스에 value 가 없을때 체크가 된 경우 : "on" (!=null) / 체크가 안된 경우 : null
//		@RequestParam < - 기본 작성시 param값 필수 요구 - null값 전달시 오류
//			ㄴ > @RequestParam(value = "value", required = false) <- 필수요구하지 않게 required = false값 설정                                               
		
		log.debug("saveId : " + saveId);
		
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

			
			/* 아이디 저장(Cookie) */
			
			// 쿠키 객체 생성(K:V)
			Cookie cookie = new Cookie("saveId", loginMember.getMemberEmail());	
			
			// 클라이언ㄴ트가 어떤 요청을 할 때 쿠키가 첨부될지 지정
			cookie.setPath("/");
		
			// 만료 기간 지정
			// 아이디 저장 체크시
			if(saveId != null)	cookie.setMaxAge(30 * 24 * 60 * 60); // 초단위로 지정
			else	cookie.setMaxAge(0); // 0초 (클라이언트 쿠키 삭제)
			
			//응답 객체에 쿠키 추가 -> 클라이언트로 전달
			resp.addCookie(cookie);
		}
		
		return "redirect:/";
	}

	
	/**로그아웃 : Session 저장된 로그인된 회원 정보를 없앰(만료, 무효화, 완료)
	 * @param SessionStatus : 세션을 완료(없앰) 시키는 역할의 객체. @SessionAttribute로 등록된 세션을 완료
	 * 			ㄴ 서버에서 기존 세션 객체가 사라짐과 동시에 새로운 세션 객체가 생성되어 클라이언트와 연결
	 *
	 * @return "redirect:/"
	 */
	@GetMapping("logout")
	public String logout(SessionStatus status) {
		
		status.setComplete(); // 세션을 완료 (없앰)
		
		return "redirect:/"; // 메인 페이지로 리다이렉트
	}

	
	/** 로그인 페이지로 이동
	 * @return
	 */
	@GetMapping("login")
	public String loginPage() { return "member/login"; }
	
	
	/** 회원가입 페이지로 이동
	 * @return
	 */
	@GetMapping("signup")
	public String signUpPage() { return "member/signup"; }
	
	

	/** 회원가입
	 * @param inputMember : 입력된 회원정보
	 *		(memberEmail, memberPw, memberNickname, memberTel)	
	 *
	 * @param memberAddress : 입력한 주소 input 3개의 값을 배열로 전달
	 *
	 * @param ra : 리다이렉트시 request scope로 데이터 전달하는 객체
	 *
	 * @return
	 */
	@PostMapping("signup")
	public String signup(/* @ModelAttribute */ Member inputMember, RedirectAttributes ra) {
		
		// 회원가입 서비스 호출
		int result = service.signup(inputMember);
		
		String path = null;
		String message = null;
		
		if(result > 0) {
			
			message = inputMember.getMemberNickname() + "님의 가입을 환영합니다.😀😀";
			path = "/";
			
		} else {
			
			message = "회원가입 실패";
			path = "/signup";
		}
		
		ra.addFlashAttribute("message", message);
		return "redirect:" + path;
	}
	
	/** 이메일 중복 겁사
	 * @param memberEmail
	 * @return 중복시 1, 아니면 0
	 */
	@ResponseBody // 응답 본문(요청한 fetch())로 돌려보냄 
	@GetMapping("checkEmail")
	public int checkEmail(@RequestParam("memberEmail") String memberEmail) {
		
		return service.checkEmail(memberEmail);
	}
	
	
	/** 닉네임 중복 겁사
	 * @param memberNickname
	 * @return 중복시 1, 아니면 0
	 */
	@ResponseBody
	@GetMapping("checkNickname")
	public int checkNickname(@RequestParam("memberNickname") String memberNickname) {
		
		return service.checkNickname(memberNickname);
	}
	
	// ------------------------------
	

	/**빠른 로그인
	 * @param memberEmail
	 * @param model
	 * @param ra
	 * @return
	 */
	@GetMapping("quickLogin")
	public String quickLogin(@RequestParam("memberEmail") String memberEmail, Model model, RedirectAttributes ra) {
		
		Member loginMember = service.quickLogin(memberEmail);
		
		if(loginMember == null) {
			ra.addFlashAttribute("message", "해당 이메일 회원이 존재하지 않습니다.");
		} else {
			model.addAttribute("loginMember", loginMember);
		}
		
		return "redirect:/";
	}
	
	/** 회원 목록 조회(비동기)
	 * @return
	 */
	@ResponseBody
	@GetMapping("selectMemberList")
	public List<Member> selectMemberList() { return service.selectMemberList();	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

			/* ************************* */
			/* Cookie란? 클라이언트 측(브라우저)에서 관리하는 데이터(파일 형식) 
			 * 
			 * 		- Cookie내 만료기간, 데이터(key=value), 사용하는 사이트(주소)가 기록
			 * 		- 클라이언트가 쿠키에 기록된 사이트로 요청을 보낼때 요청에 쿠키가 담겨 서버로 넘어감
			 * 		- Cookiie의 생성, 수정, 삭제는 Server가 관리. 저장은 Client가 함
			 * 		- Cookie는 HttpServletResponse 이용생성, 클라이언트에게 전달(응답)
			 * */
			
			/* ************************* */