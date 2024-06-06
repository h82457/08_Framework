package com.project.foodpin.main.model.service;

import java.util.List;
import java.util.Map;

import com.project.foodpin.store.model.dto.Store;

public interface MainService {

	// 메인에 가게 4개 일단 최신 순으로 가져오기
	List<Store> selectMainStore();
	
	/** 카테고리 종류 조회
	 * @return
	 */
	List<Map<String, Object>> selectCategoryTypeList();


}
