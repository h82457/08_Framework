package com.sm.book.main.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sm.book.main.service.MainService;
import com.sm.book.model.dto.Book;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class MainController {

	private final MainService service;
	
	
	
	// 메인 페이지
	@RequestMapping("/")
	public String mainPage() { return "main"; }
	

	/**	도서 전체 조회
	 * @return
	 */
	@ResponseBody
	@GetMapping("selectBookList")
		public List<Book> selectBookList() {	return service.selectBookList();	}
	
	
	// 책등록 페이지로 이동
	@GetMapping("regist")
	public String regist() {
		
		return "regist";
	}
	
	
	/** 도서 등록, update
	 * @param bookTitle
	 * @param bookWriter
	 * @param bookPrice
	 * @param ra
	 * @return 
	 */
	@GetMapping("insertBook")
	public String insertBook(@ModelAttribute Book inputBook, RedirectAttributes ra) {
		
		int result = service.insertBook(inputBook);
		
		String message = "";
		String path = "";
		
		if(result > 0) {
			message = "등록 성공!!!";
			path = "/";
		}
		else {
			message = "등록 실패...";
			path = "regist";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	// 책관리 페이지로 이동
	@GetMapping("manage")
	public String manage() {	return "manage";	}
	
	
}
