package edu.kh.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.todo.model.dto.Todo;
import edu.kh.todo.model.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;





/*
 * @ResponseBody : java -> Js 
 * 	ㄴ> 컨트롤러 메서드의 반환 값을 HTTP 응답 본문에 직접 바인딩하는 역할임을 명시
 * 	   ~> 컨트롤러 메서드의 반환값을 비동기(ajax) 요청했던 HTML/JS 파일 부분에 값을 돌려 보낼것이다를 명시
 * 
 * 				ㄴ> foward/redirect로 인식 X
 * 
 * @RequestBody : js->java
 * ㄴ> 비동기요청 (ajax)시 전달되는 데이터중 body 부분에 포함된 데이터를 알맞은 java 객체 타입으로 바인딩하ㅡㄴㄴ 어노테이션
 * 	~> 비동기 요펑시 body에 담긴 값을(STirng )  알맞은 타입으ㅗㄹ 변환해서 매개 변수에 저장
 * */


@RequestMapping("ajax")
@Controller
@Slf4j
public class AjaxController {

	@Autowired // 등록된 Bean중 같은 타입/상속관계 Bean을 해당 필드에 의존성 주입(DI)
	private TodoService service;
	
	@GetMapping("main")
	public String ajaxMain() {
		
		return "ajax/main";
	}
	
	// 전체 Todo 개수 조회 <- forward/redirect(X), >전체 Todo 개수< 라는 데이터가 반환되는것을 원함!
	@ResponseBody // <- 값 그대로 돌려보낼것
	@GetMapping("totalCount")
	public int getTotalCount() {
		
		log.info("getTotalCount() 메서드 호출됨!!!!");
//		int totalCount = 12;
		int totalCount = service.getTotalCount();
		
		return totalCount;
	}
	// 완료된 Todo 개수 조회 
	@ResponseBody // <- 값만을 돌려보낼것을 명시
	@GetMapping("completeCount")
	public int getCompleteCount() {
		
		return service.completeCount();
	}
	
	@ResponseBody // 비동기 요청 결과로 값을 반환
	@PostMapping("add")
	public int addTodo(
// 		JSON이 파라미터로 전달된 경우 아래 방법으로 얻어오기 불가능
//		@RequestParam("todoTitle") String todoTitle,
//		@RequestParam("todoContent") String todoContent
		@RequestBody Todo todo // 요청 body에 담긴 갑을 todo에 저장
		
		) {
		log.debug(todo.toString());
		
		int result = service.addTodo(todo.getTodoTitle(), todo.getTodoContent());
		return result;
	}
	
	
	// 할 일 목록 조회
	@ResponseBody
	@GetMapping("selectList")
	public List<Todo> selectList() {
		
		List<Todo> todoList = service.selectList();

		return todoList;															// (JSONArray)
//		ㄴ> List(java 전용타입) 반환,vJs가 인식 불가 <- HttpMessageConverter가 JSON 형태 {(), ()}로 변환+반환
	}
	
	// 할 일 상세 조회
	@ResponseBody // 요청한 곳으로 데이터 반환
	@GetMapping("detail")
	public Todo SelectTodo(@RequestParam("todoNo") int todoNo) {
		// return 자료형: Todo -> HttpMessageConverter가 String(Json) 형태로 변환해서 반환
		
		return service.todoDetail(todoNo);
	}
	
	// Delete 방식 요청 처리 (비동기 요청만 가능!!)
	@ResponseBody
	@DeleteMapping("delete")
	public int todoDelete(@RequestBody int todoNo) {
		
		return service.todoDelete(todoNo);
	}
	
	// 할일 완료 상태 변경
	@ResponseBody
	@PutMapping("complete")
	public int changeComplete(@RequestBody Todo todo ) {
		
		return service.changeComplete(todo);
	}
	
	
	@ResponseBody
	@PutMapping("update")
	public int todoUpdate(@RequestBody Todo todo) {
		
		return service.todoUpdate(todo);
	}
	
	
	
	
	
	
	
	
	
}
