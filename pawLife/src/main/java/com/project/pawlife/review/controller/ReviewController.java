package com.project.pawlife.review.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.pawlife.adoption.model.dto.Adopt;
import com.project.pawlife.review.model.dto.Review;
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
	

	
	// 리뷰 게시판 리스트로 이동
	@GetMapping("reviewList")
	public String reviewPage(@RequestParam(value="cp",required=false, defaultValue="1") int cp, Model model) { 
		
		Map<String, Object> map = service.selectReviewList(cp);
		
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("reviewList", map.get("reviewList"));
		
		return "review/reviewList";
	}
	

	// 후기 상세 페이지 이동
	@GetMapping("reviewDetail")
	public String reviewDetail() {
		return "review/reviewDetail";
	}
	
	
	/** 리뷰 게시글 상세 조회
	 * @return
	 */
	@GetMapping("reviewList/{reviewNo:[0-9]+}")
	public String adoptionDetail(@PathVariable("reviewNo") int reviewNo, Model model, RedirectAttributes ra) {
		
		Map<String, Integer> map = new HashMap<>();
		map.put("reviewNo",reviewNo);
		
		Review review = service.selectOneReview(map);
		
		String path = null;
		
		if(review == null) {
			
			path = "redirect:/review/reviewList";
			ra.addFlashAttribute("message","게시글이 존재하지 않습니다.");
			
		}
		else {
			path = "review/reviewDetail";
			model.addAttribute("review",review);
		}
		
		return path;
	}
	
	
	
	
	
	// 후기 글쓰기화면으로 이동
	@GetMapping("reviewWrite")
	public String reviewWrite() { return "review/reviewWrite"; }
	
	
	/** 후기 글 작성 (로그인 세션 등록시 회원번호 추가해서 insert 진행 수정)
	 * @param map
	 * @return
	 */
	@PostMapping("reviewWrite")
	public String reviewWrite(Review inputReivew, Model model) {

		// 추가할것) boardCode, 로그인한 회원 번호 inputReivew에 세팅 <- pathVariable, sessionattribute

		int result = service.reviewWrite(inputReivew);
		
		String path = "";
		String message = "";
		
		if(result > 0) {
			path = "reviewList"; // reviewDetail 구현 전까지 임시로 포워드하는 경로
			message = "후기글 등록이 완료되었습니다.";
			
		} else {
			path = "reviewWrite";
			message = "후기글 등록이 실패되었습니다.";
		}
		
		model.addAttribute("message", message);
		
		return "redirect:" + path;
	}

	
	
	/** 후기 수정 페이지
	 * @return
	 */
	@GetMapping("reviewUpdate")
	public String reviewUpdate() {
		return "review/reviewUpdate";
	}

	
}
