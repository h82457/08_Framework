package com.project.pawlife.review.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.pawlife.review.model.dto.Comment;

@Mapper
public interface CommentMapper {

	/** 댓글 목록 조회
	 * @param boardNo
	 * @return
	 */
	List<Comment> select(int reviewNo);

	/** 댓글 등록
	 * @param comment
	 * @return
	 */
	int insert(Comment comment);

	/** 댓글 수정
	 * @param comment
	 * @return
	 */
	int update(Comment comment);

	/** 댓글 삭제
	 * @param commentNo
	 * @return
	 */
	int delete(int commentNo);


}
