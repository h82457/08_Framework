package com.project.pawlife.review.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.pawlife.review.model.dto.Comment;
import com.project.pawlife.review.model.mapper.CommentMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class CommentServiceImpl implements CommentService{

	private final CommentMapper mapper;

	// 댓글 조회
	@Override
	public List<Comment> select(int reviewNo) {
		
		return mapper.select(reviewNo);
	}

	// 댓글 등록
	@Override
	public int insert(Comment comment) {

		return mapper.insert(comment);
	}

	// 댓글 수정
	@Override
	public int update(Comment comment) {

		return mapper.update(comment);
	}

	// 댓글 삭제
	@Override
	public int delete(int commentNo) {

		return mapper.delete(commentNo);
	}
}
