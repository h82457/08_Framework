package edu.kh.todo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.todo.model.dto.Todo;
import edu.kh.todo.model.service.TodoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j // 로그 객체 자동 생성
@Controller // 요청/응답 제어 역할 명시 +Bean 등록
public class MainController {
	
	// 필드
	@Autowired // 등록된 Bean중 같은 타입 or 상속관계 DI (의존성 주입)
	private TodoService service; // <- 기본적으로 null
//	ㄴ> 이미 빈에 serviceImpl이 등록되어있어서 새로 객체 생성X, 대신 만들어진것과 연결만 해줌
	
	
	
	@RequestMapping("/")
	public String mainPage(Model model) {
			
		
		log.debug("service : " + service);
//		 ㄴ> 의존성 미주입시 : 기본값인 null, @Autowired로 의존성 주입시 서비스객체가 들어감
		
		Map<String, Object> map = service.selectAll();
		
		// map 에 담긴 내용 추출
		List<Todo> todoList = (List<Todo>)map.get("todoList");
		int completeCount = (int)map.get("completeCount");
		
		model.addAttribute("todoList", todoList);
		model.addAttribute("completeCount", completeCount);
		
		return "common/main";
	}
	
	
}
