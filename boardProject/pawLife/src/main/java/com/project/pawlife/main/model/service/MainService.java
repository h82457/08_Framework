package com.project.pawlife.main.model.service;

import java.util.List;

import com.project.pawlife.adoption.model.dto.Adopt;

public interface MainService {

	/** 메인에 adopt 게시글 뿌리기
	 * @return
	 */
	List<Adopt> selectMainAdopt();

}
