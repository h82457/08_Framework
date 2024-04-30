package com.project.pawlife.myPage.model.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.pawlife.myPage.model.mapper.MyPageMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class) // 모든 예외 발생 시 롤백
@RequiredArgsConstructor
//@PropertySource("classpath:/config.properties") // config에서 가져와서 쓰겠다는 의미
public class MyPageServiceImpl implements MyPageService{

	
	private final MyPageMapper mapper;

}
