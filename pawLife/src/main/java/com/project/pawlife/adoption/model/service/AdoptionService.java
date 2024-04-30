package com.project.pawlife.adoption.model.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.project.pawlife.adoption.model.dto.Adopt;

public interface AdoptionService {

	// 게시글 작성
	int adoptionInsert(Adopt inputAdopt, MultipartFile thumnailImg, int memberNo);

	/** 게시글 리스트
	 * @param cp
	 * @return map
	 */
	Map<String, Object> selectAdoptList(int cp);

	/** 게시글 상세 조회
	 * @param map
	 * @return
	 */
	Adopt selectOneAdopt(Map<String, Integer> map);

	/** 게시글 북마크 체크/ 해제
	 * @param map
	 * @return result
	 */
	int bookCheck(Map<String, Integer> obj);

	/** 게시글 수정
	 * @param adoptInput
	 * @param thumnailImg 
	 * @return
	 */
	int adoptUpdate(Adopt adoptInput, MultipartFile thumnailImg,int statusCheck);

	/** 입양 게시글 삭제
	 * @param map
	 * @return
	 */
	int adoptDelete(Map<String, Integer> map);

}
