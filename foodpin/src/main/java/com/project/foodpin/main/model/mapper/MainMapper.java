package com.project.foodpin.main.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.project.foodpin.store.model.dto.Store;

@Mapper
public interface MainMapper {

	List<Store> selectStoreList();
	
	/** 카테고리 종류 조회
	 * @return
	 */
	List<Map<String, Object>> selectCategoryTypeList();


}
