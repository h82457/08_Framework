package com.project.pawlife.review.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.pawlife.review.model.dto.Comment;
import com.project.pawlife.review.model.service.CommentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("comment")
public class CommentController {
	
	private final CommentService service;
	
	
	/** 댓글 조회
	 * @param boardNo
	 * @return
	 */
	@ResponseBody
	@GetMapping(value="", produces = "application/json")
	public List<Comment> select(@RequestParam("reviewNo") int reviewNo) {
		
		return service.select(reviewNo);
	}
	
	/** 댓글 등록
	 * @param boardNo
	 * @return
	 */
	@ResponseBody
	@PostMapping("")
	public int insert(@RequestBody Comment comment) {
		
		return service.insert(comment);
	}
	
	/** 댓글 수정
	 * @param boardNo
	 * @return
	 */

	@PutMapping("")
	public int update(@RequestBody Comment comment) {
		
		return service.update(comment);
	}
	
	/** 댓글 삭제
	 * @param boardNo
	 * @return
	 */
	@ResponseBody
	@DeleteMapping("")
	public int delete(@RequestBody int commentNo) {
		
		return service.delete(commentNo);
	}
	
	
	
	

}
