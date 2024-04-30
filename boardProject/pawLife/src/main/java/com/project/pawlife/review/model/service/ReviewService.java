package com.project.pawlife.review.model.service;

import com.project.pawlife.review.model.dto.Review;
import com.project.pawlife.review.model.dto.UploadFile;

public interface ReviewService {

	/** 게시글 작성
	 * @param inputReivew
	 * @return
	 */
	int reviewWrite(Review inputReivew);

	/** 게시글 이미지 저장
	 * @param img
	 * @return
	 */
	int ImageUpload(UploadFile img);




}
