package com.project.pawlife.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.project.pawlife.member.model.dto.Member;

@Mapper
public interface MemberMapper {

	/** 로그인 
	 * @param memberEmail
	 * @return loginMember
	 */
	public Member login(String memberEmail);

	/**  회원 가입
	 * @param inputMember
	 * @return result
	 */
	public int signup(Member inputMember);


}
