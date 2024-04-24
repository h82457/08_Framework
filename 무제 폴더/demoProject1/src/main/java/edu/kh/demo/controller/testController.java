package edu.kh.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

// - Java 객체 : new 연산자에 의해 Heap 영역에 작성된 클래스의 내용대로 생성된 것
// - instance : 개발자가 만들고 관리하는 객체
// - Bean : Spring Container(Spring)가 만들고 관리하는 객체

@Controller	 // 요청, 응답을 제어할 컨트롤러 역할 명시 + Bean으로 등록 (== 객체로 생성해서 스프링이 관리)
// ㄴ> 실행시 스프링이 객체로 관리


public class testController {
	
//	 - 기존 Servlet : 클래스 단위로 하나의 요청만 처리 가능 (클래스에 매핑) (/a /b /c 따로 작성)
//	 - Spring : 메서드 단위로 요청 처리 가능 (메서드에 매핑) (/a /b /c 요청을 하나의 메서드에 작성)
	
	
	// @RequestMapping("요청주소") : 요청 주소를 처리할 메서드를 매핑하는 어노테이션
	
//		ㄴ 1) 메서드에 작성 : - 요청 주소와 메서드를 매핑, GET/POST 가리지않고 매핑 (속성에서 지정 가능, 실사용X)
	
//		ㄴ 2) 클래스에 작성 : 공통 주소를 매핑.
//								ex) /todo/insert, /todo/select, /todo/update
	
//								@RequestMapping("todo") 
//								public class 클래스명 {
	
//									@RequestMapping("insert")	// /todo/insert 매핑
//									public String 메서드명(){}
	
//									@RequestMapping("select")	// /todo/select 매핑
//									public String 메서드명(){}	}
	
	/*****************************************************/
	// String Boot Controller에서 특수한 경우를 제외하고 //
	// 매핑 주소 제일 앞에 "/" 를 작성하지 않는다!!!	 //
	/*****************************************************/
	
	@RequestMapping("test") // /test 요청시 처리할 메서드 매핑
	
	public String testMethod() {
		
		System.out.println("/test 요청 받음");
		
//		Controller 메서드의 반환형이 String 인 이유 <- 메서드에서 반환되는 문자열이 forward 할 html 파일의 경로가 되기 때문!
		
//		Thymeleaf : JSP 대신 사용하는 템플릿 엔진
//			ㄴ> classpath : == src\main\resources\
//			ㄴ> 접두사 : classpath:/templates/
//			ㄴ> 접미사 : .html
		
		// 경로 : src\main\resources\templates\test.html
		// 생략시 ...		=>test (접두사, 접미사, forward 설정은 view Resolver 객체에서 담당)
		
		return "test"; // (== String path = "~"; req.getDis~(path).forward... )
	}
}



















