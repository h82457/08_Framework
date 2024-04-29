package com.project.pawlife.myPage.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.pawlife.adoption.model.dto.Adopt;
import com.project.pawlife.member.model.dto.Member;
import com.project.pawlife.review.model.dto.Review;

@Mapper
public interface MyPageMapper {

	/** 로그인한 회원이 작성한 게시글 조회
	 * @return
	 */
	List<Review> selectReview(int memberNo);

	/** 로그인한 회원이 작성한 댓글 조회
	 * @param memberNo
	 * @return
	 */
	List<Review> selectComment(int memberNo);
	
	/** 로그인한 회원이 북마크한 게시물 조회
	 * @param memberNo
	 * @return bookmarkList
	 */
	List<Adopt> selectBookMark(int memberNo);
	
	/** 프로필 이미지 변경
	 * @param mem
	 * @return result
	 */
	int profile(Member mem);


}
