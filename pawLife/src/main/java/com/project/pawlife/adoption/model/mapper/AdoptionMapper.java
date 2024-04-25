package com.project.pawlife.adoption.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdoptionMapper {

	// 입양게시글작성
	int adoptionWrite(Map<String, Object> map);

}
