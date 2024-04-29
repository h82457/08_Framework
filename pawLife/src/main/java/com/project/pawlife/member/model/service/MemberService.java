package com.project.pawlife.member.model.service;

import com.project.pawlife.member.model.dto.Member;

public interface MemberService {

	/** 회원 로그인 
	 * @param inputMember
	 * @return loginMember
	 */
	Member login(Member inputMember);

	/** 빠른 로그인
	 * @param memberEmail
	 * @return loginMember
	 */
	Member quickLogin(String memberEmail);

	/** 회원 가입
	 * @param inputMember
	 * @return
	 */
	int signup(Member inputMember);

	/** 이메일 중복 검사
	 * @param memberEmail
	 * @return
	 */
	int emailCheck(String memberEmail);

	/** 닉네임 중복 검사
	 * @param memberNickname
	 * @return
	 */
	int checkNickname(String memberNickname);

	/** 전화번호 중복 검사
	 * @param memberTel
	 * @return
	 */
	int checkTel(String memberTel);

	
}
