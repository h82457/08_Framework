package com.project.pawlife.review.model.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.project.pawlife.common.util.Pagination;
import com.project.pawlife.common.util.Utility;
import com.project.pawlife.review.model.dto.Review;
import com.project.pawlife.review.model.mapper.ReviewMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional

public class ReviewServiceImpl implements ReviewService{

	private final ReviewMapper mapper;

	
	@Value("${my.review.web-path}")
	private String reviewWebPath;

	@Value("${my.review.folder-path}")
	private String reviewFolderPath;
	
	// 후기 게시글 작성
	@Override
	public int reviewWrite(Review inputReivew, MultipartFile thumnailImg, int memberNo) {
	
		String updatePath = null;
		String rename = null;
		
		if( !thumnailImg.isEmpty()) {
			rename = Utility.fileRename(thumnailImg.getOriginalFilename());
			updatePath = reviewWebPath + rename;
			
			inputReivew.setThumnail(updatePath);
			inputReivew.setMemberNo(memberNo);
		}
		
		int result = mapper.reviewWrite(inputReivew);
		
		if(result < 0) {
			try {
				thumnailImg.transferTo(new File(reviewFolderPath + rename));
			} catch (Exception e) {
			
			}
		}
		
		return result;
	}



	// 후기 게시판 리스트
	@Override
	public Map<String, Object> selectReviewList(int cp) {
		
		int listCount = mapper.getListCount();
		
		Pagination pagination = new Pagination(cp, listCount);
		
		int limit = pagination.getLimit();
		int offset = (cp-1)*limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Review> reviewList = mapper.selectReviewList(rowBounds);
		
		Map<String, Object> map = new HashMap<>();
		map.put("pagination", pagination);
		map.put("reviewList", reviewList);
		
		return map;
	}


	// 게시글 상세 조회
	@Override
	public Review selectOneReview(Map<String, Integer> map) {
		
		return mapper.selectOneReview(map);
	}



	
}
