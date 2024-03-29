package edu.kh.project.email.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.project.email.model.service.EmailService;
import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("email")
@RequiredArgsConstructor // final 필드에 자동으로 의존성 주입 (@Autowird 생성자 방식 코드 자동 완성)
public class EmailController {

	
	private final EmailService service; // final인데 초기화 없어서 오류, 

	@ResponseBody
	@PostMapping("signup")
	public int signup(@RequestBody String email) {
		String authKey = service.sendEmail("signup", email);
		
		if(authKey != null) { // 인증번호가 반환되서 돌아옴
							  // == 이메일 보내기 성공
			
			
			return 1;
		}
		
		// 이메일 보내기 실패
		return 0;
	}
	
	
	




	

	
}


	/* @Autowired를 이용한 의존성 주입 방법은 3가지가 존재
	 * 
	 * 1) 필드(권장 X)
	 * 	// 필드에 의존성 주입하는 방법 
	//@Autowired // 의존성 주입(DI)
	// private EmailService service;
	 * 
	 * 2) setter
	 * 
	 * 
	 * 3) 생성자	
	 * 
	 * 
	 * Lombok 라이브러리(자주쓰는 코드 자동 완성)에서 제공하는
	 * 		@RequiredArgsConstructor를 이용시 필드중
	 * 
	 * 	ㄴ 1) 초기화 안된 final이 붙은 필드
	 * 	ㄴ 2) 초기화 안된 @NotNull이 붙은 필드
	 * 
	 * 		=> 1,2에 해당하는 필드에 대한 @Autowired 생성자 구문을 자동 완성
	 *  */

	// 2. setter 이용
	//private EmailService service;
	//@Autowired
	//public void setService(EmailService service) { this.service = service; }
	
	
	// 생성자
//	private EmailService service;
//	private MemberService service2;
//
//	@Autowired
//	public EmailController(EmailService service, MemberService service2) {
//		this.service = service;
//		this.service2 = service2;
//	}