package com.project.pawlife.review.model.service;

import java.util.Map;

import com.project.pawlife.review.model.dto.Review;

public interface ReviewService {

	// 게시글 작성
	int reviewWrite(Review inputReivew);

	/** 게시글 리스트
	 * @param cp
	 * @return map
	 */
	Map<String, Object> selectReviewList(int cp);

	/** 게시글 상세 조회
	 * @param map
	 * @return
	 */
	Review selectOneReview(Map<String, Integer> map);




}
