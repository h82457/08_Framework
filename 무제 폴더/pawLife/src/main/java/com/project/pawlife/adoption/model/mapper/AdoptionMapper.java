package com.project.pawlife.adoption.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.project.pawlife.adoption.model.dto.Adopt;

@Mapper
public interface AdoptionMapper {

	
	/** 게시글 작성
	 * @param inputAdopt
	 * @return
	 */
	int adoptionInsert(Adopt inputAdopt); 
	
}
