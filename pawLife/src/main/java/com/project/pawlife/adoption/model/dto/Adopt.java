package com.project.pawlife.adoption.model.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Adopt {
	
	// ADPOT 테이블 컬럼
	private int adoptNo;
	private String adoptTitle;
	private String adoptContent;
	private String adoptWriteDate;
	private String adoptUpdateDate;
	private int readCount;
	private String adoptDelFl;
	private String thumnail;
	private String adoptName;
	private String adoptAge;
	private String adoptType;
	private String adoptAddress;
	private int memberNo;
	private int boardCode;
	private String adoptCompl;
	
	// 테이블 조인
	private String memberNickname;
	
	
	// 게시글 작성자 프로필 이미지
	private String profileImg;
	
	// 북마크
	private String bookmarkCheck;
	private int bookmark;

}
