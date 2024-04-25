package com.kh.test.model.service;

import com.kh.test.model.dto.User;

public interface UserService {

	/** 회원버놓 조회
	 * @param inputNo
	 * @return
	 */
	User searchUser(String inputNo);


}
