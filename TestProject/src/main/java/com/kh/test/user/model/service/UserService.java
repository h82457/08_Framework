package com.kh.test.user.model.service;

import com.kh.test.user.model.dto.User;

public interface UserService {

	/** 아이디 검색
	 * @param userId
	 * @return user
	 */
	User searchId(String userId);
}
