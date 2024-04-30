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

	/** 이메일 중복 검사
	 * @param memberEmail
	 * @return
	 */
	public int emailCheck(String memberEmail);

	/**  닉네임 중복 검사
	 * @param memberNickname
	 * @return
	 */
	public int checkNickname(String memberNickname);

	/** 전화번호 중복 검사
	 * @param memberTel
	 * @return
	 */
	public int checkTel(String memberTel);


}
