package com.sm.book.main.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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
	public String mainPage() { return "common/main"; }
	
	

	/**	도서 전체 조회
	 * @return
	 */
	@ResponseBody
	@GetMapping("selectBookList")
		public List<Book> selectBookList() {	return service.selectBookList();	}
	
	// 책등록 페이지로 이동
	@GetMapping("insert")
	public String insert() {
		
		return "common/insert";
	}
	
	/** 도서 등록
	 * @param bookTitle
	 * @param bookWriter
	 * @param bookPrice
	 * @param ra
	 * @return 
	 */
	@GetMapping("insertBook")
	public String insertBook(@RequestParam("bookTitle") String bookTitle, 
			@RequestParam("bookWriter") String bookWriter, 
			@RequestParam("bookPrice") int bookPrice,
			RedirectAttributes ra) {
		
		
		
		return null;
	}
	
	
}
