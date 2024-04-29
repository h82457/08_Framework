package com.project.pawlife.myPage.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.project.pawlife.adoption.model.dto.Adopt;
import com.project.pawlife.member.model.dto.Member;
import com.project.pawlife.myPage.model.service.MyPageService;
import com.project.pawlife.review.model.dto.Review;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SessionAttributes({"loginMember"})
@Controller
@RequestMapping("myPage")
@RequiredArgsConstructor
@Slf4j
public class MyPageController {

	
	private final MyPageService service;


	//  myPage 화면 
	@GetMapping("first")
	public String first() {
		return "myPage/myPage-first";
		
		
	}
	
	
	// myPage 수정 화면으로 보내줌
   @GetMapping("myPage-profileupdate")
   public String profileUpdate() {
      
      return "myPage/myPage-profileupdate";
   }


   // 로그인한 회원이 작성한 후기 게시글 전체 조회
   @ResponseBody
   @GetMapping("selectReview")
   public List<Review> selectReview(
		@SessionAttribute("loginMember")Member loginMember){

	   int memberNo = loginMember.getMemberNo();
	   
	List<Review> reviewList = service.selectReview(memberNo);
	
	
	
	return reviewList;
   }
   
	   /** 로그인한 회원이 작성한 댓글 전체 조회
	 * @param loginMember
	 * @param ra
	 * @return
	 */
    @ResponseBody
	@GetMapping("selectComment")
	public List<Review> selectComment(@SessionAttribute("loginMember") Member loginMember,
			RedirectAttributes ra) {

		int memberNo = loginMember.getMemberNo();

		List<Review> commentList = service.selectComment(memberNo);
		/*
		 * String message=null;
		 * 
		 * if(commentList ==null ) ra.addFlashAttribute("message",message);
		 */
		 
		return commentList;
	}

   
    @GetMapping("selectBookMark")
    public List<Adopt> selectBookMark(
    	@SessionAttribute("loginMember") Member loginMember, 
    	RedirectAttributes ra
    		){
    	int memberNo = loginMember.getMemberNo();

    	List<Adopt> bookmarkList = service.selectBookMark(memberNo);
    	
    	
    	return bookmarkList;
    }

   
   /**
	 * 프로필 이미지 변경
	 * 
	 * @param profileImg
	 * @param loginMember
	 * @param ra
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */
	@PostMapping("profile")
	public String profile(@RequestParam("profileImg") MultipartFile profileImg,
			@SessionAttribute("loginMember") Member loginMember, RedirectAttributes ra)
			throws IllegalStateException, IOException {

		// 로그인한 회원 번호
		int memberNo = loginMember.getMemberNo();

		// 서비스 호출
		// /myPage/profile/변경된파일명 형태의 문자열
		// 현재 로그인한 회원의 PROFILE_IMG 컬럼 값으로 수정(UPDATE)
		int result = service.profile(profileImg, loginMember);

		String message = null;

		if (result > 0)
			message = "변경 성공";
		else
			message = "변경 실패";

		ra.addFlashAttribute("message", message);

		return "redirect:profile";
	}

}
