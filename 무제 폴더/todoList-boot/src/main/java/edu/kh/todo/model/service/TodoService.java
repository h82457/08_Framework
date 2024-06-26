package edu.kh.todo.model.service;

import java.util.List;
import java.util.Map;

import edu.kh.todo.model.dto.Todo;

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

	/** 상세 조회
	 * @param todoNo
	 * @return todo
	 */
	Todo todoDetail(int todoNo);

	/** 할 일 삭제
	 * @param todoNo
	 * @return result
	 */
	int todoDelete(int todoNo);

	/** 할 일 수정
	 * @param todo
	 * @return result
	 */
	int todoUpdate(Todo todo);
	
	/** 할 일 완료 수정
	 * @param todo
	 * @return result 
	 */
	int changeComplete(Todo todo);

	/** 전체 Todo 개수 조회
	 * @return totalCount
	 */
	int getTotalCount();

	/** 완료된 Todo 개수 조회 
	 * @return completeCount
	 */
	int completeCount();

	/** 할 일 목록 조회
	 * @return todoList
	 */
	List<Todo> selectList();

}
