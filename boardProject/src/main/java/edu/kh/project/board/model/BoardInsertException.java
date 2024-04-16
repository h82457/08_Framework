package edu.kh.project.board.model;

/**
 * 게시글 삽입중 문제 발생시 사용할 사용자 정의 예외
 */
public class BoardInsertException extends RuntimeException{

	public BoardInsertException() {
		super("게시글 삽입중 예외 발생");
	}

	public BoardInsertException(String message) {
		super(message);
	}
}
