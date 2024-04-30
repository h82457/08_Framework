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
	
	
	/** 개인정보 수정 (닉네임/ 전화번호 )
	 * @param inputMember
	 * @return
	 */
	int profileUpdate(Member inputMember);


	/** 비밀번호 수정
	 * @param memberNo
	 * @param currentPw
	 * @param newPw
	 * @return result
	 */
	int changeMemberPw(int memberNo, String currentPw, String newPw);


	/** 프로필 이미지 변경
	 * @param profileImg
	 * @param loginMember
	 * @return result
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	int profile(MultipartFile profileImg, Member loginMember) throws IllegalStateException, IOException;

	
	/** 회원 탈퇴
	 * @param memberNo
	 * @return
	 */
	int deleteMember(int memberNo);



}
