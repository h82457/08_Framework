package com.project.pawlife.review.model.dto;

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
public class Review {

   // REIVEW 테이블 컬럼 추가
   private int reviewNo;
   private String reviewTitle;
   private String reviewContent;
   private String reviewWriteDate;
   private String reviewUpdateDate;
   private int readCount;
   private String reviewDelFl;
   private int memberNo;
   private int boardCode;
   private String thumnail;
   
   // Comment
   private int commentNo;
   private String commentContent;
   private String commentWriteDate;
   private String commentDelFl;

   
	// 댓글 조회시 회원 프로필,닉네임
	private String memberNickname;
	private String profileImg;
	   
   
   
}