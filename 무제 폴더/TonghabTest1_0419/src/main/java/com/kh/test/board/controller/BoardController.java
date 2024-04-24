package com.kh.test.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.test.board.model.dto.Board;
import com.kh.test.board.model.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BoardController {

	private final BoardService service;
	
	/** 제목 검색
	 * @param boardTitle
	 * @param model
	 * @return
	 */
	@GetMapping("search")
	public String searchTitle(@RequestParam("boardTitle") String boardTitle, Model model) {
		
		List<Board> boardList = service.searchTitle(boardTitle);

		String path = "";
		
		if( !boardList.isEmpty()) {
			path = "searchSuccess";
			model.addAttribute("boardList", boardList);
		}
		else path = "searchFail";

		return path;
	}
	
}
