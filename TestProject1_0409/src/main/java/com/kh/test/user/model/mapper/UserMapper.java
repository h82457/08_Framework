package com.kh.test.user.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.kh.test.user.model.dto.User;

@Mapper
public interface UserMapper {

	
	/** 아이디 검색
	 * @param userId
	 * @return list
	 */
	User searchId(String userId);
}
