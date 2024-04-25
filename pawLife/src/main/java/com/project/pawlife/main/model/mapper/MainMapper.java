package com.project.pawlife.main.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.pawlife.adoption.model.dto.Adopt;

@Mapper
public interface MainMapper {

	/** 메인 입양 게시글 란 select
	 * @return
	 */
	List<Adopt> selectMainAdopt();

}
