package edu.kh.project.board.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.kh.project.board.model.dto.Comment;
import edu.kh.project.board.model.service.CommentService;
import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
@RequestMapping("comment")
public class CommentController {

	private final CommentService service;
	
	

	/** 댓글 조회
	 * 
	 * "/comment?boardNo=" + boardNo
	 *   ㄴ> 요청 주소와 클래스 상단에서 매핑한 주소가 같은경우 빈칸("")으로 작성
	 *   
	 * @param boardNo
	 * @return commentList
	 */
	@GetMapping(value="", produces = "application/json")  // <- 형태로 작성할 경우 안전, value 속성 : 매핑할 주소, produces 속성 : 응답할 데이터의 형식을 지정
	public List<Comment> select(@RequestParam("boardNo") int boardNo) {
		
		// HttpMessageConverter가 List -> JSON (문자열)로 변환해서 응답
		
		return service.select(boardNo);

	}
	
	/** 댓글 등록
	 * @param comment
	 * @return result
	 */
	@PostMapping("")
	public int insert(@RequestBody Comment comment) { return service.insert(comment); }
//						ㄴ> 요청 데이터가 패치의 헤더에서 JSON으로 명시되어 전달됨
//							~> Arguments Resolver가 JSON을 DTO(Comment)로 자동 변경 (JACKSON 라이브러리 기능)
		
	
	
	/** 댓글 수정
	 * @param comment
	 * @return result
	 */
	@PutMapping("")
	public int update(@RequestBody Comment comment) { return service.update(comment); }
	
	/** 댓글 삭제
	 * @param commentNo
	 * @return result
	 */
	@DeleteMapping("")
	public int delete(@RequestBody int commentNo) { return service.delete(commentNo); }
	
	
}
