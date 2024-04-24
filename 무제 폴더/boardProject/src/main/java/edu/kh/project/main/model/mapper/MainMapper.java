package edu.kh.project.main.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MainMapper {

	// 비밀번호 초기화
	int resetPw(Map<String, Object> map);
	
	// 회원 탈퇴 여부 조회
	String selectSec(int inputNo);
	
	// 탈퇴 회원 복구
	int restoration(int inputNo);



}
