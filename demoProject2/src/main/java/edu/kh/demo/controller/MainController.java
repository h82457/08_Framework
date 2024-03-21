package edu.kh.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// Controller : 요청에 따라 알맞은 서비스 호출 할지 제어
//				+ 서비스 결과에 따라 어떤 응답을 할지 제어

@Controller		// 요청,응답 제어 역할 명시 + Bean 등록
public class MainController {
	
	@RequestMapping("/")	// "/" 주소 요청시 해당 메서드와 매핑
//					ㄴ> 메인 페이지 지정시 "/" 작성 가능
	public String mainPage() {
		
/*		
				[thymeleaf]
				
			Spring Boot에서 사용하는 템플릿 엔진
			thymeleaf를 이용한 html 파일로 forward시 사용되는 접두사, 접미사가 존재
			
			- 접두사 : classpath:/templates/
			- 접미사 : .html
*/		
		return "common/main"; // <- 경로로 forward
	}
}
