package com.project.foodpin.myPage.model.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.project.foodpin.member.model.dto.Member;
import com.project.foodpin.reservation.model.dto.Reservation;
import com.project.foodpin.review.model.dto.Review;
import com.project.foodpin.store.model.dto.Store;

@Mapper
public interface MemberMyPageMapper {

	// 회원 정보 수정
	int updateInfo(Member mem);

	// 회원 비밀번호 변경
	int memberChangePw(Map<String, Object> paramMap);

	// 노쇼 횟수 조회
	int noshowCount(int memberNo);
	
	// 예약 확정 조회
	List<Reservation> reservationFix(int memberNo);
	
	// 예약 대기 조회
	List<Reservation> reservationWait(int memberNo);
	
	// 지난 예약 조회
	List<Reservation> reservationLast(int memberNo);
	
	// 예약 취소/노쇼 조회
	List<Reservation> reservationCancelNoshow(int memberNo);
	
	// 예약 취소 기능
	int cancelReservation(Map<String, Integer> map);
	
	// 찜 목록 조회
	List<Store> memberLikeList(int memberNo);

	// 찜 개수
	int likeCount(int memberNo);
	
	// 찜 취소
	int cancelLike(Map<String, Integer> cancelLike);

	// 리뷰 목록 조회
	List<Review> selectReviewList(int memberNo);
	
	// 리뷰 개수
	int reviewCount(int memberNo);

	// 리뷰 삭제
	int reviewDelete(int reviewNo);
	
	// 입력받은 현재 비번과 DB에서 조회한 비번 비교
	String selectPw(int memberNo);
	
	// 회원 탈퇴하기
	int secession(int memberNo);

	// 회원 탈퇴 전 예약 확정/대기 조회
	int checkReserv(int memberNo);


















}
