package com.project.pawlife.myPage.model.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.project.pawlife.adoption.model.dto.Adopt;
import com.project.pawlife.member.model.dto.Member;
import com.project.pawlife.review.model.dto.Review;

public interface MyPageService {


	/** 로그인한 회원이 쓴 후기 게시글 조회
	 * @param memberNo 
	 * @return reviewList
	 */
	List<Review> selectReview(int memberNo);

	/** 로그인한 회원이 쓴 댓글 조회
	 * @param memberNo
	 * @return commentList
	 */
	List<Review> selectComment(int memberNo);
	
	/** 로그인한 회원이 북마크한 게시물 조회
	 * @param memberNo
	 * @return bookmarkList
	 */
	List<Adopt> selectBookMark(int memberNo);


	/** 프로필 이미지 변경
	 * @param profileImg
	 * @param loginMember
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	int profile(MultipartFile profileImg, Member loginMember) throws IllegalStateException, IOException;


}
