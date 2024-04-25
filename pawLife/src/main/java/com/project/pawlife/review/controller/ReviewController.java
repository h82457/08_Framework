package com.project.pawlife.review.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.project.pawlife.review.model.service.ReviewService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.proxy.annotation.Post;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("review")
public class ReviewController {

	@Autowired
	private final ReviewService service;
	
	/** 리뷰 게시판 리스트로 이동
	 * @return
	 */
	@GetMapping("reviewList")
	public String reviewPage() {
		return "review/reviewList";
	}
	
	/** 후기 글쓰기화면으로 이동
	 * @return
	 */
	@GetMapping("reviewWrite")
	public String reviewWrite() {
		return "review/reviewWrite";
	}
	

	/** 후기 글 작성
	 * @param map
	 * @return
	 */
	@PostMapping("reviewWrite")
	public String reviewWrite(@RequestBody Map<String, Object> map) {

		int result = service.reviewWrite(map);
		
		return "redirect:/";
	}
	
	
	
	
	
	
	
	
	
	
	
	/** 후기 상세 페이지
	 * @return
	 */
	@GetMapping("reviewDetail")
	public String reviewDetail() {
		return "review/reviewDetail";
	}
	
	/** 후기 수정 페이지
	 * @return
	 */
	@GetMapping("reviewUpdate")
	public String reviewUpdate() {
		return "review/reviewUpdate";
	}

	
}
