package edu.kh.project.board.model.exception;

/**
 * 이미지 삭제중 문제 발생시 사용할 사용자 정의 예외
 */
public class ImageUpdateException extends RuntimeException{

	public ImageUpdateException() {
		super("이미지 수정/삽입중 예외 발생");
	}

	public ImageUpdateException(String message) {
		super(message);
	}
}
