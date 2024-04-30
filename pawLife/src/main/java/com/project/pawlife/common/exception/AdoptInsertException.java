package com.project.pawlife.common.exception;


/** 게시글 삽입 중 문제 발생 시 사용할 사용자 저으이 예외
 * 
 */
public class AdoptInsertException extends RuntimeException{

	public AdoptInsertException() {
		super("입양 게시글 이미지 삽입 중 예외 발생");
	}
	
	public AdoptInsertException(String message) {
		super(message);
	}

	
}
