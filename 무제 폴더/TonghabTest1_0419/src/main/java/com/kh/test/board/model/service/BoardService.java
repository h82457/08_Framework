package com.kh.test.board.model.service;

import java.util.List;

import com.kh.test.board.model.dto.Board;

public interface BoardService {

	/** 제목 검색
	 * @param boardTitle
	 * @return list
	 */
	List<Board> searchTitle(String boardTitle);

}
