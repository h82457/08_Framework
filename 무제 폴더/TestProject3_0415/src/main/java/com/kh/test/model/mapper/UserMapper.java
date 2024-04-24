package com.kh.test.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.kh.test.model.dto.User;

@Mapper
public interface UserMapper {

	// 회ㅜ언번호 조회
	User searchUser(String inputNo);

}
