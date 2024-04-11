package edu.kh.project.board.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.kh.project.board.model.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("board")
public class BoardController {

	private final BoardService service;
	
	/** 게시글 목록 조회
	 * @param boardCode : 게시판 종류
	 * @param cp : 현재 조회 요청한 페이지 (없으면 1, 있으면 cp=2 cp=3...)
	 * @return
	 * 
	 * - /board/000, /board 이하 1레벨 자리에 숫자로된 요청 주소가 작성되어있을떄만 동작 -> 정규표현식 이용
	 * [0-9] : 한칸에 0~9 사이 숫자 입력 가능 / + : 하나 이상 => [0-9]+ : 모든 숫자, 숫자로 된 모든 요청
	 */
	@GetMapping("{boardCode:[0-9]+}") 
	public String selectBoardList(@PathVariable("boardCode") int boardCode,
//									ㄴ>경로에 있는 주소를 얻어와서 변수에 저장
			@RequestParam(value="cp", required = false, defaultValue = "1") int cp, Model model) {
	
		// 조회 서비스 호출후 결과 반환
		Map<String, Object> map = service.selectBoardList(boardCode, cp);
		
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("boardList" , map.get("boardList"));
		
		
		return "board/boardList"; // boardList.html로 forward
	}
	 
}
