package edu.kh.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.todo.model.dto.Todo;
import edu.kh.todo.model.service.TodoService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller // 역할 명시 + 빈 등록
@RequestMapping("todo")  // /todo로 시작하는 요청 매핑
public class TodoController {

	@Autowired // 같은 타입 빈 의존성 주입 (DI)
	private TodoService service;
	
	// 할 일 추가
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
	
	// 상세 조회
	@GetMapping("detail")
	public String todoDetail(@RequestParam("todoNo") int todoNo, Model model, RedirectAttributes ra) { 
							// ㄴ> String 타입으로 전달받았어도 int로 적으면 알아서 데이터 파싱 해줌
								// ㄴ> RedirectAttributes : 리다이렉트시 데이터를 request scope에 전달 가능한 객체
		
		Todo todo = service.todoDetail(todoNo); // PK(todoNo)로 조회시 1행, 한 행의 결과를 한번에 저장할 객체 ->Todo
		
		String path = null;
		
		if(todo != null) { //조회 결과 있을 경우
			
			path = "todo/detail"; // forward 경로
			model.addAttribute("todo", todo); // request scope에 값 세팅
			
		} else {//조회 결과 없을 경우
			
			path = "redirect:/"; // 메인페이이지로 리다이렉트
			ra.addFlashAttribute("message", "해당 할 일이 존재하지 않습니다."); 
		}
		return path;
	}

	/** 할 일 삭제
	 * @param todoNo : 삭제할 할 일 번호 (PK)
	 * @param ra : 리다이렉트시 1회성으로 데이터 전달하는 객체
	 * @return 메인 페이지/상세페이지
	 */
	@GetMapping("delete")
	public String todoDelete(@RequestParam("todoNo") int todoNo, RedirectAttributes ra) {
		
		int result = 0;
		String path = "";
		String message = "";
		result = service.todoDelete(todoNo);
		
		if(result > 0) { // 성공시 "삭제 성공", 메인 페이지 리다이렉트
			
			path = "/";
			message = "삭제 성공!!!";
		
		} else { // 실패시 "삭제 실패", 상세 조회 페이지로 리다이렉트
			path = "todo/detail";
			message = "삭제 실패...";
		} 
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}

	
	/** 수정 화면 전환
	 * @param todoNo : 수정 할 일 번호
	 * @param model : 데이터 전달 객체 (기본 : request)
	 * @return todo/update.html <- forward
	 */
	@GetMapping("update")
	public String todoUpdate(@RequestParam("todoNo") int todoNo, Model model) {
		
		// 상세 조회 서비스 호출 -> 수정 화면에 출력할 수정 이전 내용
		Todo todo = service.todoDetail(todoNo);
		
		model.addAttribute("todo", todo);
		
		return "todo/update";
	}
	

	/** 할 일 수정 <- getMapping과 이름이 같으나 매개변수의 갯수가 달라 오버로딩되어 사용 가능
	 * @param updateTodo : 커맨드 객체 - 전달 받은 파라미터가 자동으로 필드에 세팅된 객체, requestParam을 여러개 작성 필요 X
	 * @return 
	 */
	@PostMapping("update")
	public String todoUpdate(@ModelAttribute Todo todo, RedirectAttributes ra) {
		
		// 수정 서비스 호출
		int result = service.todoUpdate(todo);
		
		String path = "redirect:";
		String message = "";
		
		if(result > 0) { // 상세 조회로 리다이렉트
			path += "/todo/detail?todoNo=" + todo.getTodoNo();
			message = "수정 성공!!!";
			
		} else { // 다시 수정화면으로 리다이렉트(리다이렉트는 기본적으로 get 방식.)
			path += "/todo/update?todoNo=" + todo.getTodoNo();
			message = "수정 실패...";
		}
		ra.addFlashAttribute("message", message);
		
		return path;
	}
	
		
	/** 완료 여부 변경
	 * @param todo : 커맨드 객체 (@ModelAttribute 생략) - todoNo, complete 두 필드가 세팅된 상태
	 * @param complete 완료 여부 수정할 내용
	 * @param ra 리다이렉트시 1회성으로 데이터 전달하는 객체
	 * @return 상세페이지 "detail?todoNo=" + 할 일 번호 (상대경로)
	 */
	@GetMapping("changeComplete")
	public String changeComplete(Todo todo, RedirectAttributes ra) {
		
		int result = 0;
		String message = "";
		
		result = service.changeComplete(todo);
		
		if(result > 0) message = todo.getComplete() + "로 수정";
		
		else message = "수정 실패..";
		
		ra.addFlashAttribute("message", message);

		// 현재 요청 주소 : /todo/chanceComplete, 응답 주소 : /todo/detail
		return "redirect:detail?todoNo=" + todo.getTodoNo();
	}
	

	
	
	
	
	
}
