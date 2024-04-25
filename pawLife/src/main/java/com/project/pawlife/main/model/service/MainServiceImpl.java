package com.project.pawlife.main.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.pawlife.adoption.model.dto.Adopt;
import com.project.pawlife.main.model.mapper.MainMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MainServiceImpl implements MainService{

	
	@Autowired
	private final MainMapper mapper;

	/** 입양 게시글
	 *
	 */
	@Override
	public List<Adopt> selectMainAdopt() {
		return mapper.selectMainAdopt();
	}

}
