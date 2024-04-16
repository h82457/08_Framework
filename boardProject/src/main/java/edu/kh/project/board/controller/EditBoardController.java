package edu.kh.project.board.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.board.model.dto.Board;
import edu.kh.project.board.model.service.EditBoardService;
import edu.kh.project.member.model.dto.Member;
import lombok.RequiredArgsConstructor;



@Controller	
@RequiredArgsConstructor
@RequestMapping("editBoard/")
public class EditBoardController {

	private final EditBoardService service;
	
	
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
	public String boardInsert(@PathVariable("boardCode") int boardCode, Board inputBoard,
			@SessionAttribute("loginMember") Member loginMember, @RequestParam("images") List<MultipartFile> images,
			RedirectAttributes ra) throws IllegalStateException, IOException {
		
		/* 전달되는 파라미터 확인 */
//		
//		List<MultipartFile> 
//			ㄴ 5개 모두 업로드		=> 0~4번 인덱스에 파일 저장됨
//		 	ㄴ 5개 모두 업로드 X	=> 0~4번 인덱스 파일 저장 X
//			ㄴ 2번 인덱스만 업로드	=> 2번 인덱스만 파일 저장 나머지인 0, 1, 3, 4번 인덱스는 저장 X
		
//		[문제점]  파일이 선택되지 않은 input 태그도 데이터가 빈칸으로 제출 
//					ㄴ> 파일 선택이 안된 input 태그 값을 서버에 저장하려고 하면 오류가 발생
		
//		[해결방법]  무작정 서버에 저장 X, 제출된 파일이 있는지 확인하는 로직을 추가 구성
//						+ List 요소의 index 번호 == IMG_ORDER 와 같음
		
		
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
	
	
	@GetMapping("{boardCode:[0-9]+}/{boardNo:[0-9]+}/delete")
	public String boardDelete(@PathVariable("boardCode") int boardCode, @PathVariable("boardNo") int boardNo) {
		
		int result = service.boardDelete(boardNo);
		
		String path = "";
		
		if(result > 0) path = "/board/" + boardCode;
		else path = "board/" + boardCode + "/" + boardNo;
		
		return "redirect:" + path;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}