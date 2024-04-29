package com.project.pawlife.adoption.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
	 * @param cp : 현재 조회 요청한 페이지(없으면 1)
	 * @return
	 */
	@GetMapping("adoptionList")
	public String adoptionPage(
			@RequestParam(value="cp",required=false, defaultValue="1") int cp /*페이지 수*/,
			Model model
			) {
		
		// 조회 서비스 요청 후 결과 반환
		Map<String, Object> map = service.selectAdoptList(cp);
		
		model.addAttribute("pagination",map.get("pagination"));
		model.addAttribute("adoptList",map.get("adoptList"));
		
		
		return "adoption/adoptionList";
	}
	
	/** 입양 상세 조회
	 * @return
	 */
	@GetMapping("adoptionList/{adoptNo:[0-9]+}")
	public String adoptionDetail(
			@PathVariable("adoptNo") int adoptNo,
			Model model,
			RedirectAttributes ra
			) {
		
		Map<String, Integer> map = new HashMap<>();
		map.put("adoptNo",adoptNo);
		
		
		Adopt adopt = service.selectOneAdopt(map);
		
		String path = null;
		
		if(adopt == null) {
			
			path = "redirect:/adoption/adoptionList";
			ra.addFlashAttribute("message","게시글이 존재하지 않습니다.");
			
		}
		else {
			path = "adoption/adoptionDetail";
			model.addAttribute("adopt",adopt);
		}
		
		return path;
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
			@RequestParam("adoptContent") String adoptContent,
			@SessionAttribute("loginMember") Member loginMember
			) {


		int memberNo = loginMember.getMemberNo(); 
		
		int result = service.adoptionInsert(inputAdopt,thumnailImg,memberNo);
		
		
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
	
	
	@ResponseBody
	@PostMapping("bookMark")
	public int bookMark(
			 @RequestBody Map<String,Integer> map 
			) {
		
		
		
		 return service.bookCheck(map);
	}
	

}
