package com.project.foodpin.main.model.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.project.foodpin.main.model.mapper.MainMapper;
import com.project.foodpin.store.model.dto.Store;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService{

	private final MainMapper mapper;

	// 가게 리스트 조회
	@Override
	public List<Store> selectMainStore() {
		
		
		List<Store> storeList = mapper.selectStoreList();
		
		 return storeList;
	}
	
	
	// 카테고리 종류 조회
	@Override
	public List<Map<String, Object>> selectCategoryTypeList() {
		
		return mapper.selectCategoryTypeList();
	}
	
	
}
