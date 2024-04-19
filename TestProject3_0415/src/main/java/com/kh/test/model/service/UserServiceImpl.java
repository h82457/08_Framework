package com.kh.test.model.service;

import org.springframework.stereotype.Service;

import com.kh.test.model.dto.User;
import com.kh.test.model.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	private final UserMapper mapper;

	// 회원번호 조회
	@Override
	public User searchUser(String inputNo) { return mapper.searchUser(inputNo); }
	
	
}
