package edu.kh.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.todo.model.service.TodoService;
import org.springframework.web.bind.annotation.PostMapping;


@Controller // 역할 명시 + 빈 등록
@RequestMapping("todo")  // /todo로 시작하는 요청 매핑
public class TodoController {

	@Autowired // 같은 타입 빈 의존성 주입 (DI)
	private TodoService service;
	
	@PostMapping("add") // /todo/add Post 방식 요청 매핑
	public String addTodo(
			@RequestParam("todoTitle") String todoTitle,
			@RequestParam("todoContent") String todoContent,
			RedirectAttributes ra
			){
		
		
		// RedirectAttributes : 리다이렉트 시 값을 1회성으로 전달하는 객체
		
		// RedirectAttributes.addFlashAttribute("key", value) 형식으로 잠깐 세션에 속성 추가
		
		// [원리]
		// 응답 전 : request scope
		// redirect 중 : session scope로 이동
		// 응답 후 : reqeust scope로 복귀
		
		
		// 서비스 메서드 호출후 결과 반환 받기
		int result = service.addTodo(todoTitle, todoContent);
		
		// 삽입 결과에 따라 message값 지정
		String message = null;
		
		if(result > 0) message = "할 일 추가 성공!!!";
		else	message = "할 일 추가 실패...";
				
		ra.addFlashAttribute("message", message);
		
		return "redirect:/"; //메인 페이지 재요청
	}
	
	
	
	
}
