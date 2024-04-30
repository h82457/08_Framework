package com.project.pawlife.adoption.controller;


import java.io.IOException;
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
			RedirectAttributes ra,
			@SessionAttribute(value="loginMember", required = false) Member loginMember
			) {
		
		Map<String, Integer> map = new HashMap<>();
		if(loginMember!=null) {
			int memberNo = loginMember.getMemberNo();
			map.put("memberNo",memberNo);
		}
		
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
			@SessionAttribute(value="loginMember", required = false) Member loginMember
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
	
	
	/** 입양 수정 화면 전환
	 * @return
	 */
	@GetMapping("editAdoption/{adoptNo:[0-9]+}/update")
	public String adoptionUpdate(
			@PathVariable("adoptNo") int adoptNo,
			@SessionAttribute("loginMember") Member loginMember,
			RedirectAttributes ra,
			Model model
			) {
		
		Map<String, Integer> map = new HashMap<>();
		map.put("adoptNo",adoptNo);
		Adopt adopt = service.selectOneAdopt(map);
		
		
		String message = null;
		String path = null;
		
		if(adopt == null) {
			message = "해당 게시글이 존재하지 않습니다.";
			path = "redirect:/adoption/adoptionList"; // 메인페이지로 리다이렉트 
			
			ra.addFlashAttribute("message",message);
			
		}else if(adopt.getMemberNo() != loginMember.getMemberNo()) {
			message = "자신이 작성한 글만 수정할 수 있습니다.";
			// 해당 글 상세 조회
			path = String.format("redirect:/adoption/adoptionList/%d",adoptNo);
			
			ra.addFlashAttribute("message",message);
			
		}else {
			path ="adoption/adoptionUpdate";
			
			// forward의 경우
			model.addAttribute("adopt",adopt);
		}
		
		return path;
	}
	
	/** 수정하기
	 * @param adoptNo
	 * @param adoptInput
	 * @param loginMember
	 * @param thumnailImg
	 * @param deleteOrder
	 * @param querystring
	 * @param ra
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	@PostMapping("editAdoption/{adoptNo:[0-9]+}/update")
	public String editAdopt(
			@PathVariable("adoptNo") int adoptNo,
			Adopt adoptInput,
			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam("thumnailImg") MultipartFile thumnailImg,
			@RequestParam(value="deleteOrder", required=false)String deleteOrder,
			@RequestParam(value="querystring", required=false, defaultValue="")String querystring,
			@RequestParam("statusCheck") int statusCheck,
			RedirectAttributes ra
			) throws IllegalStateException, IOException{
		
		
		int memberNo = loginMember.getMemberNo();
		adoptInput.setMemberNo(memberNo);
		
		int result = service.adoptUpdate(adoptInput,thumnailImg,statusCheck);
		
		String message = null;
		String path = null;
		
		if(result>0) {
			message = "게시글이 수정되었습니다.";
			path = String.format("/adoption/adoptionList/%d%s", adoptNo, querystring);
		}else {
			message = "수정 실패";
			path = "update"; // 수정화면 전환 상대 경로
		}
		
		ra.addFlashAttribute("message",message);
		
		return "redirect:"+path;
		
	}
	
	
	@PostMapping("editAdoption/{adoptNo:[0-9]+}/delete")
	public String deleteAdopt(
			@SessionAttribute("loginMember") Member loginMember,
			@PathVariable("adoptNo") int adoptNo,
			RedirectAttributes ra
			) {
		
		int memberNo = loginMember.getMemberNo();
		
		Map<String, Integer> map = new HashMap<>();
		map.put("memberNo", memberNo);
		map.put("adoptNo", adoptNo);
		
		int result = service.adoptDelete(map);
		
		String path = null;
		String message = null;
		
		if(result>0) {
			path = "/adoption/adoptionList";
			message="삭제 되었습니다.";
		}else {
			path = "/adoption/adoptionList/"+adoptNo;
			message="삭제 실패.";
		}
		
		ra.addFlashAttribute("message",message);
		
		return "redirect:"+path;
	}
	
	
	
	
	/** 북마크
	 * @param map
	 * @return 
	 */
	@ResponseBody
	@PostMapping("bookmark")
	public int bookMark(
			 @RequestBody Map<String,Integer> map 
			) {
		
		
		
		 return service.bookCheck(map);
	}
	
	
	
	
	

}
