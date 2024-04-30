package com.project.pawlife.review.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.project.pawlife.review.model.dto.Review;
import com.project.pawlife.review.model.dto.UploadFile;

@Mapper
public interface ReviewMapper {

	
	/** 게시글 작성
	 * @param inputReivew
	 * @return result
	 */
	int reviewWrite(Review inputReivew);

	
	/** 게시글 이미지 저장
	 * @param img
	 * @return result
	 */
	int ImageUpload(UploadFile img);

}
