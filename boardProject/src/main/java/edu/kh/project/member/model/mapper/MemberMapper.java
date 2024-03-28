package edu.kh.project.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import edu.kh.project.member.model.dto.Member;

@Mapper
public interface MemberMapper {

	public Member login(String memberEmail);

	// 회원가입
	public int signup(Member inputMember);
}
