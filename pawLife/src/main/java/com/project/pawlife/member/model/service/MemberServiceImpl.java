package com.project.pawlife.member.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.pawlife.member.model.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;

@Transactional // 예외 발생 시 rollback 해줌
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

private final MemberMapper mapper;
	
}
