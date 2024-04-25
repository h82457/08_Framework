package edu.kh.project.board.model.service;

import java.util.List;
import java.util.Map;

import edu.kh.project.board.model.dto.Board;

public interface BoardService {

	// 게시글 타입 테이블은 컬럼이 2개, int나 String 으로 읽어오기가 불가능+DTO도 미존재 => 맵으로 묶어서 읽어옴
	
//		ㄴ {"boardCode" :1,					{"boardCode" :2,						<- 하나하나가 Map 이고 그 Map을 묶어서 List로 읽어옴
//			"boardName" : "공지 게시판},  	 "boardName" : "정보 게시판}, ... 				(K:V, K:V) <- 게시판 한컬럼
	
	/** 게시판 종류 조회, 
	 * @return boardTypeList <~ 호출한 BoardTypeInterceptor로 값 전달
	 */
	List<Map<String, Object>> selectBoardTypeList();

	/** 특정 게시판의 지정된 페이지 목록 조회
	 * @param boardCode
	 * @param cp
	 * @return map 
	 */
	Map<String, Object> selectBoardList(int boardCode, int cp);

	
	
	/** 게시글 상세 조회
	 * @param map
	 * @return board
	 */
	Board selectOne(Map<String, Integer> map);

	/** 게시글 좋아요 체크/해제
	 * @param map
	 * @return result
	 */
	int boardLike(Map<String, Integer> map);

	/** 게시글 조회수 증가
	 * @param boardNo
	 * @return
	 */
	int updateReadCount(int boardNo);

	/** 게시글 검색
	 * @param paramMap
	 * @param cp
	 * @return map
	 */
	Map<String, Object> searchList(Map<String, Object> paramMap, int cp);
}

