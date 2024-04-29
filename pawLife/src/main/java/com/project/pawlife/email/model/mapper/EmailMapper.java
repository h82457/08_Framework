package com.project.pawlife.email.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmailMapper {

	/** 이전에 이메일 인증을 한 이메일인 경우 (update)
	 * @param map
	 * @return
	 */
	int updateAuthKey(Map<String, String> map);

	/** 최초 인증
	 * @param map
	 * @return
	 */
	int insertAuthKey(Map<String, String> map);

	/** 이메일, 인증번호 확인
	 * @param map
	 * @return
	 */
	int checkAuthKey(Map<String, Object> map);

}
