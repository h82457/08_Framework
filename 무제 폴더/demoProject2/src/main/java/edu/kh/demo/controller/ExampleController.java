package edu.kh.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.demo.model.dto.Student;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller		// 요청/응답 역할 명시 + Bean 등록

@RequestMapping("example")
// 			ㄴ>/example로 시작하는 주소를 해당 컨트롤러에서 매핑
@Slf4j		// lombok 라이브러리가 제공하는 로그 객체 자동 생성 @

public class ExampleController {

	
	
	/* Model
	 *  
	 * - Spring 에서 데이터 전달 역할을 하는 객체
	 * - org.springframework.ui 패키지
	 * - 기본 scope : request
	 * - @SessionAttributes와 함께 사용시 session scope 변환 (<- request/session scope 두가지 사용 가능)
	 * 
	 * [기본 사용법]
	 * Model.addAttribute("key", value);
	 * 
	 * (Model과 비슷한 ModelAndView도 존재)
	 * - ModelAndView : 데이터 전달 + forward 할 파일 경로 지정
	 * */
	
	
	
//	/example/ex1 GET 방식 요청을 매핑
	@GetMapping("ex1") 
	public String ex1(HttpServletRequest req, Model model) { // model improt: spring.~.ui
									
		// Servlet/JSP 내장 객체 범위(scope) : page < request < session < application

		// request scope
		req.setAttribute("test1", "HttpServletRequest로 전달한 값");
		model.addAttribute("test2", "Model로 전달한 값");
		
		
		// 단일 값(숫자, 문자열) Model을 이용해서 html로 전달
		model.addAttribute("productName", "종이컵");
		model.addAttribute("price",  2000);
		
		
		// 복수 값(배열, List) Model을 이용해서 html로 전달
		List<String> fruitList = new ArrayList<>();
		fruitList.add("사과");
		fruitList.add("딸기");
		fruitList.add("바나나");
		
		model.addAttribute("fruitList", fruitList);
		
		
		// DTO 객체 Model을 이용해서 html로 전달
		Student std = new Student();
		std.setStudentNo("12345");
		std.setName("홍길동");
		std.setAge(22);
		
		model.addAttribute("std",std);
		
		// List<Student> 객체 Model을 이용 html로 전달
		List<Student> stdList = new ArrayList<>();
		
		stdList.add(new Student("111", "김일번", 20));
		stdList.add(new Student("222", "최이번", 22));
		stdList.add(new Student("333", "홍삼번", 23));
		
		model.addAttribute("stdList", stdList);
		
		return "example/ex1";	// templates/example/ex1.html로 요청 위임
	}
	
	
//	/example/ex2 POST 방식 요청을 매핑
	@PostMapping("ex2")
	public String ex2(Model model) {
		
		// Model : 데이터 전달용 객체(Request scope)
		model.addAttribute("str", "<h1> 테스트 중 &times; </h1>");
		
		return "example/ex2";
	}

//	/example/ex3 GET 방식 요청을 매핑	
	@GetMapping("ex3")
	public String ex3(Model model) {
		
		model.addAttribute("boardNo", 10);
		model.addAttribute("key", "제목");
		model.addAttribute("query", "검색어");
		
		return "example/ex3";
	}
	
	/*			 [@pathVariable]
	 *  - 주소 중 일부분을 변수값 처럼 사용
	 *  - 해당 어노테이션으로 얻어온 값은 request scope에 세팅
	 *  */

	@GetMapping("ex2/{number}")
	public String pathVariableTest(
		@PathVariable("number") int number
		// 주소중 {number} 부분의 값을 가져와 매개변수에 저장 + requestScope 세팅
		) {
		
		log.debug("number : " + number);
		
		
		return "example/testResult";
	}
	
	@GetMapping("ex4")
	public String ex4(Model model) {
		
		Student std = new Student("67890", "잠만보", 22);
		
		model.addAttribute("std", std);		
		model.addAttribute("num", 100);		
		
		return "example/ex4";
	}
	
	@GetMapping("ex5")
	public String ex5(Model model) {
		
		// Model : Spring에서 값 전달 역할을 하는 객체. 기본적으로는 request scope + session으로 확장 가능
		
		model.addAttribute("message", "타임리프 + Javascript 사용 연습");
		model.addAttribute("num1", 12345);
		
		Student std = new Student();
		
		std.setStudentNo("123");
		
//		model.addAttribute("std",std);
		
		return "example/ex5";
	}
	
}



