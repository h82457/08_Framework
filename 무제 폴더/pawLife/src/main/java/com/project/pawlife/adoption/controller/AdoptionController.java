package com.project.pawlife.adoption.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.project.pawlife.adoption.model.dto.Adopt;
import com.project.pawlife.adoption.model.service.AdoptionService;
import com.project.pawlife.member.model.dto.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@RequestMapping("adoption")
@Slf4j
public class AdoptionController {
	
	@Autowired
	private final AdoptionService service;
	
	/** 입양 게시판 리스트 항목으로 이동
	 * @return
	 */
	@GetMapping("adoptionList")
	public String adoptionPage(
			) {
		return "adoption/adoptionList";
	}
	
	/** 입양 상세 조회
	 * @return
	 */
	@GetMapping("adoptionDetail")
	public String adoptionDetail() {
		return "adoption/adoptionDetail";
	}
	
	/** 입양 글쓰기 화면 이동
	 * @return
	 */
	@GetMapping("adoptionWrite")
	public String adoptionWrite() {
		
		
		
		return "adoption/adoptionWrite";
	}
	
	
	
	/** 입양 게시글 작성
	 * @param inputAdopt
	 * @param thumnailImg
	 * @return
	 */
	@PostMapping("adoptionInsert")
	public String adoptionInsert(
			Adopt inputAdopt,
			@RequestParam("thumnailImg") MultipartFile thumnailImg,
			@RequestParam("adoptContent") String adoptContent
			) {

		
		int result = service.adoptionInsert(inputAdopt,thumnailImg);
		
		String path=null;
		
		
		if(result>0) {
			
			path="/adoption/adoptionList";
			
		}else {
			path="/adoption/adoptionWrite";
		}
		
		return "redirect:"+path;
	}
	
	
	
	
	
	
	/** 입양문의
	 * @return
	 */
	@GetMapping("adoptionContact")
	public String contact() { return "adoption/adoptionContact"; }
	
	/** 입양 수정
	 * @return
	 */
	@GetMapping("adoptionUpdate")
	public String adoptionUpdate() {
		return "adoption/adoptionUpdate";
	}
	

}
