package edu.kh.project.board.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.board.model.dto.Board;
import edu.kh.project.board.model.service.BoardService;
import edu.kh.project.board.model.service.EditBoardService;
import edu.kh.project.member.model.dto.Member;
import lombok.RequiredArgsConstructor;



@Controller	
@RequiredArgsConstructor
@RequestMapping("editBoard/")
public class EditBoardController {

	private final EditBoardService service;
	private final BoardService BoardService;
	
	/** 게시글 작성 화면으로 전환
	 * @param board/boardWrite
	 * @return
	 */
	@GetMapping("{boardCode:[0-9]+}/insert")
	public String boardInsert(@PathVariable("boardCode") int boardCode) { return "board/boardWrite"; }
	
	
	/** 게시글 작성
	 * @param boardCode : 어느 게시판에 작성한 글인지 구분
	 * @param inputBoard : 입력된 값(제목, 내용) + 추가 데이터 저장 (커맨드 객체)
	 * @param loginMeber : 로그인 한 회원 번호 얻어오는 용도
	 * @param images : 제출된 file 타입 input 태그 데이터들
	 * @param ra : 리다이렉트시 request scope로 데이터 전달

	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@PostMapping("{boardCode:[0-9]+}/insert")
	public String boardInsert(@PathVariable("boardCode") int boardCode, 
			Board inputBoard,
			@SessionAttribute("loginMember") Member loginMember, 
			@RequestParam("images") List<MultipartFile> images,
			RedirectAttributes ra
			) throws IllegalStateException, IOException {
		
		// 1. boardCode, 로그인한 회원 번호를 inputBoard에 세팅
		inputBoard.setBoardCode(boardCode);
		inputBoard.setMemberNo(loginMember.getMemberNo());
		
		// 2. 서비스 메서드 호출후 결과 반환 받기 + 성공시 [상세조회]를 요청할 수 있도록 삽입된 게시글 번호 반환 받기
		int boardNo = service.boardInsert(inputBoard, images);
		
		// 3. 서비스 결과에 따라 message, 리다이렉트 경로 지정
		
		String path = null; 
		String message = null;
		
		if(boardNo > 0) {
			path = "/board/" + boardCode + "/" + boardNo; // 상세 조회 경로
			message = "게시글이 작성 되었습니다.";
		}
		else {
			path ="insert"; // 기존 작성 화면으로 이동
			message = "게시글이 작성 실패";
		}
				
		ra.addFlashAttribute("message", message);	
		
		// 게시글 작성(INSERT) 성공시 -> 작성된 게시글 상세 조회로 redirect
		return "redirect:" + path;
	}
	
	
	/** 게시글 삭제
	 * @param boardCode
	 * @param boardNo
	 * @param loginMember
	 * @return
	 */
	@GetMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}/delete")
	public String boardDelete(@PathVariable("boardCode") int boardCode, @PathVariable("boardNo") int boardNo , 
			@SessionAttribute("loginMember") Member loginMember) {
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("boardCode", boardCode);
		map.put("boardNo", boardNo);
		map.put("memberNo", loginMember.getMemberNo());
		
		int result = service.boardDelete(map);
		
		String path = "";
		
		if(result > 0) path = String.format("/board/%d", boardCode);
		else path = "/board/" + boardCode + "/" + boardNo;
		
		return "redirect:" + path;
	}
	
	
	/** 게시글 수정 화면으로 전환
	 * @param boardCode : 게시판 종류
	 * @param boardNo : 게시글 번호
	 * @param loginMember : 로그인한 회원이 작성한 글이 맞는지 검사하는 용도
	 * @param model : 포워드시 request scope로 값 전달 용도
	 * @param ra : 리다이렉트시 request scope로 값 전달 용도
	 * @return
	 */
	@GetMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}/update")
	public String boardUpdate(
			@PathVariable("boardCode") int boardCode, @PathVariable("boardNo") int boardNo,
			@SessionAttribute("loginMember") Member loginMember, Model model, RedirectAttributes ra) { 
		
		// 수정 화면에 출력할 제목/내용/이미지 조회 -> 게시글 상세 조회한 데이터 필요
		Map<String,	Integer> map = new HashMap<>(); // 게시글 상세 조회에 필요한 데이터 매개변수로 보낼 map 생성
		map.put("boardCode", boardCode);
		map.put("boardNo", boardNo);
		
		// BoardService.selectOne(map) 호출
		Board board = BoardService.selectOne(map);
		
		String message = null;
		String path = null;
		
		if(board == null) { // <- GET 방식 요청, 주소창에서 임의로 요청 변경시 존재하지 않는 게시글을 수정하는 경우
			message = "해당 게시글이 존재하지 않습니다.";
			path = "redirect:/"; // 메인 페이지
			ra.addFlashAttribute("message", message);
			
		} else if(board.getMemberNo() != loginMember.getMemberNo()) { // 로그인된 회원이 작성한 게시글이 아닌 경우
			
			message = "자신이 작성한 글만 수정할 수 있습니다.";
			path = String.format("redirect:/board/%d/%d", boardCode, boardNo); // 해당글 상세 조회 페이지
			ra.addFlashAttribute("message", message);
		
		}else { // 게시글 수정 조건에 맞는 경우 수정페이지로 이동
			path = "board/boardUpdate";
			model.addAttribute("board", board);
		}
		
		return path; 
	}
	
	
	/** 게시글 수정
	 * @param boardCode : 게시판 종류
	 * @param boardNo : 수정할 게시글 번호
	 * @param inputBoard : 커맨드 객체(제목, 내용)
	 * @param loginMember : 로그인한 회원 번호(로그인한 회원과 작성자 일치 확인)
	 * @param images : input type = "file" 모든 요소 (실제로 파일 담긴 여부 상관X)
	 * @param ra : redirect시 request scope 값 전달
	 * 
	 * 		+	+	+
	 * 
	 * @param deleteOrder : 삭제된 이미지 순서가 기록된 문자열 (1,2,3)
	 * @param querystring : 수정 성공시 이전 파라미터 유지(cp, 검색어)
	 * @return
	 */
	@PostMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}/update")
	public String boardUpdate(@PathVariable("boardCode") int boardCode, @PathVariable("boardNo") int boardNo,
			Board inputBoard, @SessionAttribute("loginMember") Member loginMember, 
			@RequestParam("images") List<MultipartFile> images, RedirectAttributes ra,
			@RequestParam(value="deleteOrder", required = false) String  deleteOrder,
			@RequestParam(value="querystring", required = false, defaultValue = "") String querystring
			) throws IllegalStateException, IOException {

		// [1] 커맨드 객체(inputBoard)에 추가로 boardCode, boardNo, memberNo 세팅 
		inputBoard.setBoardCode(boardCode);
		inputBoard.setBoardNo(boardNo);
		inputBoard.setMemberNo(loginMember.getMemberNo());
		// =>  inputBoard(제목, 내용, boardCode, boardNo, memberNo)
		
		// [2] 게시글 수정 서비스 호출후 결과 반환 받기
		int result = service.boardUpdate(inputBoard, images, deleteOrder);
		
		// [3] 서비스 결과에 따라 응답 제어
		String message = "";
		String path = "";
		
		if(result > 0) {
			message = "게시글이 수정되었습니다.";
			path = String.format("/board/%d/%d%s", boardCode, boardNo, querystring);
		
		} else {
			message = "수정 실패";
			path = "update"; // 수정 화면 전환 상대경로
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
