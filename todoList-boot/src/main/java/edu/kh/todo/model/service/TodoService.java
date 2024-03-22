package edu.kh.todo.model.service;

import java.util.Map;

public interface TodoService {

	/** 할 일 목록 + 완료된 할 일 개수 조회
	 * @return map
	 */
	Map<String, Object> selectAll();
//	Spring에서 발생하는 예외는 대부분 Unchecked 상태 -> 대부분 예외처리 불필요

	/** 할 일 추가
	 * @param todoTitle
	 * @param todoContent
	 * @return result
	 */
	int addTodo(String todoTitle, String todoContent);
}
