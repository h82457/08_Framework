package com.project.pawlife.adoption.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.pawlife.adoption.model.mapper.AdoptionMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AdoptionServiceImpl implements AdoptionService {

	
	private final AdoptionMapper mapper;
}
