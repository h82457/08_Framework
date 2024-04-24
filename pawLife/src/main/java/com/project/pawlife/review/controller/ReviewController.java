package com.project.pawlife.review.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.project.pawlife.review.model.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
	
	/** 후기 글쓰기
	 * @return
	 */
	@GetMapping("reviewWrite")
	public String reviewWrite() {
		return "review/reviewWrite";
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
