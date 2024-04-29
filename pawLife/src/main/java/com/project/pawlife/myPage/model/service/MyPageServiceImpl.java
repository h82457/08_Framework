package com.project.pawlife.myPage.model.service;


import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.project.pawlife.adoption.model.dto.Adopt;
import com.project.pawlife.common.util.Utility;
import com.project.pawlife.member.model.dto.Member;
import com.project.pawlife.myPage.model.mapper.MyPageMapper;
import com.project.pawlife.review.model.dto.Review;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class) // 모든 예외 발생 시 롤백
@RequiredArgsConstructor
//@PropertySource("classpath:/config.properties") // config에서 가져와서 쓰겠다는 의미
public class MyPageServiceImpl implements MyPageService{

	
	private final MyPageMapper mapper;
	
	@Value("${my.profile.web-path}")  // /myPage/profile/
	private String profileWebPath;
	
	@Value("${my.profile.folder-path}") // D:/uploadFiles/profile/
	private String profileFolderPath;
	
	

	// 로그인한 회원이 작성한 게시글 리스트 조회
	@Override
	public List<Review> selectReview(int memberNo) {
		
		return mapper.selectReview(memberNo);
	}
	

	// 로그인한 회원이 작성한 댓글 리스트 조회
	@Override
	public List<Review> selectComment(int memberNo) {
		
		return mapper.selectComment(memberNo);
	}
	
	// 로그인한 회원이 북마크한 게시물 조회
	@Override
	public List<Adopt> selectBookMark(int memberNo) {
	
		return mapper.selectBookMark(memberNo);
	}


	// 프로필 이미지 변경
	@Override
	public int profile(MultipartFile profileImg, Member loginMember) throws IllegalStateException, IOException {
		
		// 수정할 경로
		String updatePath = null;
		
		// 변경명 저장
		String rename = null;
		
		//업로드한 이미지가 있을 경우
		if(!profileImg.isEmpty()) {
			
			// updatePath 조합해줌
			
			 rename = Utility.fileRename(profileImg.getOriginalFilename());
			
			// /myPage/profile/변경된 파일명.jpg
			updatePath = profileWebPath + rename;
			
			// 서비스 요청이 왔을 때 있으면 파일 경로 조합
			// 없을 경우 null값이 mem에 세팅이 되어 mapper에 보내지고 update하면 됨 
			//mapper로 보내기
		}
		
		
		//  수정된 프로필 이미지 경로 + 회원 번호를 저장할 DTO 객체 만들기
		Member mem =Member.builder()
				             .memberNo(loginMember.getMemberNo())
				             .profileImg(updatePath)
		                     .build(); //-> 객체 만들어짐 -> 위에 경로를 만들어주자!
		
		// UPDATE 수행
		int result = mapper.profile(mem); 
		// 이미지가 null 없을 경우도 updatePath가 1로 업데이트 됨 -> 성공해버림 (if 사용해서 안전장치 추가)
		
		if(result > 0) { // 수정 성공 시
			// 파일을 서버 지정된 폴더에 저장 
			//profileImg.transferTo(new File(폴더 경로 + 변경명));
		
			// 프로필 이미지를 null로 수정한 경우를 제외
			//-> 진짜 업로드한 이미지가 있을 경우에 서버에 저장한다는 코드
			if(!profileImg.isEmpty()) {
			profileImg.transferTo(new File( profileFolderPath + rename));
			}
			// 세션 회원 정보에서 프로필 이미지 경로를 
			// 업데이트한 경로로 변경
			// 없는 경우 null이 들어감
		    loginMember.setProfileImg(updatePath);
		}
		return result;
	}



}
