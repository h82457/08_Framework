package edu.kh.project.main.service;

import org.springframework.stereotype.Service;


public interface MainService {

	// 비밀번호 초기화
	int resetPw(int inputNo);

	// 탈퇴 복구
	int restoration(int inputNo);

}
