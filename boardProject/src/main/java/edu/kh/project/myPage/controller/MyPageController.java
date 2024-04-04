package edu.kh.project.myPage.controller;


import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.myPage.model.service.MyPageService;
import lombok.RequiredArgsConstructor;


@SessionAttributes({"loginMember"})
@Controller
@RequestMapping("myPage")
@RequiredArgsConstructor
public class MyPageController {

	private final MyPageService service;
	
	
	/** 내 정보 조회/수정화면으로 전환
	 * @param loginMember : 세션에 존재하는 loginMember를 얻어와 매개 변수에 대입
	 * @param model : 데이터 전달용 객체 (기본 request scope)
	 * @return myPage/myPage-info.html 요청 위임
	 */
	@GetMapping("info") // /myPage/info (GET)
	public String info(@SessionAttribute("loginMember") Member loginMember, Model model) { 
		
		// 주소만 꺼내오기
		String memberAddress = loginMember.getMemberAddress();
		
		// 주소가 있을 경우만 동작
		if(memberAddress != null) {
			
			String[] arr = memberAddress.split("\\^\\^\\^"); // 구분자 "^^^"를 기준으로 memberAddress값 쪼개서 String[]로 반환
		
			model.addAttribute("postcode", arr[0]);
			model.addAttribute("address", arr[1]);
			model.addAttribute("detailAddress", arr[2]);
		}
		
		return "myPage/myPage-info";
	}
	
	
	/** 프로필 이미지 변경 화면 이동
	 * @return
	 */
	@GetMapping("profile")
	public String profile() {
		return "myPage/myPage-profile";
	}
	
	
	/** 비밀번호 변경 화면 이동
	 * @return
	 */
	@GetMapping("changePw")
	public String changePw() {
		return "myPage/myPage-changePw";
	}
	
	
	/** 회원 탈퇴 화면 이동
	 * @return
	 */
	@GetMapping("secession")
	public String secession() {
		return "myPage/myPage-secession";
	}
	
	
	/** 회원 정보 수정
	 * @param inputMember : 제출된 회원 닉네임, 전화번호, 주소
	 * @param loginMember : 로그인한 회원 정보 (회원 번호 이용해 수정)
	 * @param memberAddress : 주소만 따로 받은 String[]
	 * @param la : 리다이렉트시 request scope로 데이터 전달
	 * @return
	 */
	@PostMapping("info")
	public String updateinfo(
			/* @ModelAttribute */ Member inputMember,
			@SessionAttribute("loginMember") Member loginMember,
			@RequestParam("memberAddress") String[] memberAddress,
			RedirectAttributes ra
			) {

		// inputMember에 로그인한 회원 번호 추가
		int memberNo = loginMember.getMemberNo();
		inputMember.setMemberNo(memberNo);
		
		// 회원 정보 서비스 호출
		int result = service.updateInfo(inputMember, memberAddress);
		
		String message = null;
		
		if(result > 0) {
			message = "회원 수정 성공!!!";
			
			// loginMember는 세션에 저장된 로그인된 회원 정보가 저장된 객체를 참조 -> loginMember 수정시 세션 저장값도 수정
			loginMember.setMemberNickname( inputMember.getMemberNickname());
			loginMember.setMemberTel( inputMember.getMemberTel());
			loginMember.setMemberAddress( inputMember.getMemberAddress());
		}
		else message = "회원 수정 실패...";
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:info";
	}
	

	/** 비밀번호 수정
	 * @param loginMember
	 * @param paramMap
	 * @param ra
	 * @return
	 */
	@PostMapping("changePw")
	public String changePw(@SessionAttribute("loginMember") Member loginMember, 
			@RequestParam Map<String, Object> paramMap,
			RedirectAttributes ra) {
		
		int memberNo = loginMember.getMemberNo();
		
		
		int result = service.changePw(memberNo, paramMap);
		String message = "";
		String path;
		
		if(result > 0) {
			message = "비밀번호가 변경 되었습니다.";
			path = "/myPage/info";

		} else {
			message = "현재 비밀번호가 일치하지 않습니다.";
			path = "/myPage/changePw";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	
	// @SessionAttributes : Model에 세팅된 값 중 key가 일치하는 값을 request -> session으로 변경
	// SessionStatus : SessionAttributes 를 이용해서 올라간 데이터의 상태를 관리하는 객체 
//	  	ㄴ> 해당 컨트롤러에 @SessionAttributes({"key1", "key2"}) 가 작성되어 있는 경우 () 내 key1, key2의 상태를 관리
	
	/** 회원 탈퇴
	 * @param loginMember : 로그인한 회원 정보
	 * @param memberPw : 입력 받은 비밀번호
	 * @param status : 세션 완료(없애기) 용도의 객체 -> 
	 * @param ra
	 * @return
	 */
	@PostMapping("secession")
	public String secession(@SessionAttribute("loginMember") Member loginMember,
			@RequestParam("memberPw") String memberPw,
			RedirectAttributes ra,
			SessionStatus status) {
		
		int memberNo = loginMember.getMemberNo();
		int result = service.secession(memberNo, memberPw);
		
		String message = "";
		String path = "";
		
		if(result > 0) {
			message = "탈퇴 되었습니다.";
			status.setComplete();
			path = "/";
			
		} else {
			message = "비밀번호가 일치하지 않습니다.";
			path = "/myPage/secession";
		}
		
		ra.addFlashAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	/* 파일 업로드 테스트 1*/
	
	@GetMapping("fileTest")
	public String fileTest() { return "myPage/myPage-fileTest"; }
	
	/*	Spring에서 파일 업로드를 처리하는 방법
	 *  
	 *  enctype="multipart/form-data" 로 클라이언트 요청을 받으면
	 *  	이를 MultipartResolver를 이용해 섞여있는 파라미터 분리 (분리전에는 문자, 숫자, 파일등이 섞인 상태)
	 *  		ㄴ 문자열, 숫자 ~> String / 파일 -> MultipartFile
	 *  
	 *  */
	
	
	/** 파일 업로드 테스트1
	 * @param uploadFile : 업로드한 파일
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@PostMapping("file/test1")
	public String fileUpload1 (
			@RequestParam("uploadFile") MultipartFile uploadFile,
			RedirectAttributes ra)  throws IllegalStateException, IOException {

		String path = service.fileUpload1(uploadFile);
		
		// 파일이 저장되어 웹에서 접근할수 있는 경로가 반환되었을떄
		if( path != null)	ra.addFlashAttribute("path", path);
		
		return "redirect:/myPage/fileTest";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
 }
