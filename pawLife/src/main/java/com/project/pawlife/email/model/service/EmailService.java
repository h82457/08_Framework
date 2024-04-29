package com.project.pawlife.email.model.service;

import java.util.Map;

public interface EmailService {

	/** 이메일 발송
	 * @param email
	 * @param email2 
	 * @return authKey
	 */
	String sendEmail(String Sendemail, String email);

	/** 이메일 인증번호 비교
	 * @param map
	 * @return
	 */
	int checkAuthKey(Map<String, Object> map);
	

}
