package com.project.pawlife.adoption.model.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.project.pawlife.adoption.model.dto.Adopt;

public interface AdoptionService {

	// 게시글 작성
	int adoptionInsert(Adopt inputAdopt, MultipartFile thumnailImg);

}
