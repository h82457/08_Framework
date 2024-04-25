package com.project.pawlife.adoption.model.service;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.pawlife.adoption.model.mapper.AdoptionMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AdoptionServiceImpl implements AdoptionService {

	
	private final AdoptionMapper mapper;

	// 입양 게시글 작성
	@Override
	public int adoptionWrite(Map<String, Object> map) { return mapper.adoptionWrite(map) ; }
}
