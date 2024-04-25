package com.project.pawlife.review.model.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.pawlife.review.model.mapper.ReviewMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewServiceImpl implements ReviewService{

	private final ReviewMapper mapper;

	@Override
	public int reviewWrite(Map<String, Object> map) { return mapper.reviewWrite(map) ; }
	
}
