package com.project.pawlife.member.model.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.pawlife.member.model.dto.Member;
import com.project.pawlife.member.model.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;

@Transactional // 예외 발생 시 rollback 해줌
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

private final MemberMapper mapper;

@Autowired
private BCryptPasswordEncoder bcrypt;

// 회원 로그인
@Override
public Member login(Member inputMember) {
	
	// 1. bcrypt.encode 암호화
	String bcryptPassword = bcrypt.encode( inputMember.getMemberPw());
	
	// 이메일이 일치하면서 탈퇴하지 않은 회원 조회함 -> loginMember에 담음
	Member loginMember = mapper.login(inputMember.getMemberEmail()); 
	
	// 일치하는 이메일이 없는 경우
	 if(loginMember == null) return null; 
	
	
	// 3. 입력 받은 비밀번호랑 암호화된 비밀번호 비교
	// 불일치
	
	
	 if( !bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw())) {
	  
	  return null;
	 
	 }
	
	 
	// 위를 통과해서 일치한 경우 session에서 비밀번호 제거
	
	  loginMember.setMemberPw(null);
	
		
	return loginMember;  
}

   // 빠른 로그인
   // loginMember를 반환해줌
	@Override
	public Member quickLogin(String memberEmail) {
		
		Member loginMember = mapper.login(memberEmail);
		
		// 회원이 없는 경우 null 돌려보냄
		if(loginMember == null) return null;
		
		// 회원 있는 경우 session에 올라간 비밀번호 null로 지우기
		loginMember.setMemberPw(null);
		
		return loginMember;
	}

	// 회원 가입
	@Override
	public int signup(Member inputMember) {
		
		// 입력된 비밀 번호를 암호화 하여 inputMember에 세팅
		String encPw= bcrypt.encode(inputMember.getMemberPw());
		
		inputMember.setMemberPw(encPw);
		
		return mapper.signup(inputMember);
	}
		
}
