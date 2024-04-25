package com.project.pawlife.review.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReviewMapper {

	// 게시글 작성
	int reviewWrite(Map<String, Object> map);

}
