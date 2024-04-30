package com.project.pawlife.review.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
	private int commentNo;
	private String commentContent;
	private String commentDelFl;
	private int memberNo;
	private int reviewNo;
	private String commentWriteDate;
	
	// 댓글 조회시 회원 프로필, 닉네임
	private String memberNickname;
}
