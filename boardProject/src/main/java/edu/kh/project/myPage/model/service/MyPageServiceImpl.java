package edu.kh.project.myPage.model.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.common.util.Utility;
import edu.kh.project.member.model.dto.Member;
import edu.kh.project.myPage.model.dto.UploadFile;
import edu.kh.project.myPage.model.mapper.MyPageMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class) // 모든 예외 발생시 롤백
@RequiredArgsConstructor
@PropertySource("classpath:/config.properties")
public class MyPageServiceImpl implements MyPageService{

	private final MyPageMapper mapper; // final 필드의 값을 의존성 주입 
	
	private final BCryptPasswordEncoder bcrypt;

	@Value("${my.profile.web-path}")
	private String profileWebPath;
	
	@Value("${my.profile.foler-path}")
	private String profileFolderPath;
	
	
	// 		@RequiredArgsConstructor 를 이용했을 때 자동 완성 되는 구문
	//	@Autowired
	//	public MyPageServiceImpl(MyPageMapper mapper) { this.mapper = mapper; }


	// 회원 정보 수정
	@Override
	public int updateInfo(Member inputMember, String[] memberAddress) {
		
		// 주소 입력 미입력시 inputMember.getMemberAddress() 값 -> ",," ~> 주소에 null을 대입
		if( inputMember.getMemberAddress().equals(",,")) inputMember.setMemberAddress(null);
		
		else {
			// memberAddress를 A^^^B^^^C^^^ 형태로 가공
			String address = String.join("^^^", memberAddress);
		
			// 주소에 가공된 데이터 대입
			inputMember.setMemberAddress(address);
		}
		return mapper.updateInfo(inputMember);
	}


	// 비밀번호 변경
	@Override
	public int changePw(int memberNo,  Map<String, Object> paramMap) {
		
//		- bcrypt 암호화된 비밀번호를 DB에서 조회(회원 번호 필요)
		
		String originPw = mapper.selectPw(memberNo);
		
//		- 현재 비밀번호 == bcrypt 암호화된 비밀번호 비교 (  BcryptPasswordEncoder.matches(평문, 암호화된 비밀번호)   )
		// 다를 경우 -> 실패 ( 0 반환 )	
		if(! bcrypt.matches((String)paramMap.get("currentPw"), originPw) )  return 0; 
		
//		- 같을 경우 -> 새 비밀번호를 암호화 진행
		String encPw = bcrypt.encode((String)paramMap.get("newPw"));
		
		paramMap.put("currentPw", encPw);
		paramMap.put("memberNo", memberNo);
		
//		 -> 새 비밀번호로 변경(UPDATE)하는 Mapper 호출
//		    회원 번호, 새 비밀번호 -> 하나로 묶음 (Member 또는 Map) 
//		    -> 결과 1 또는 0 반환
		
		return mapper.updatePw(paramMap);
	}

	// 회원 탈퇴
	@Override
	public int secession(int memberNo, String memberPw) {

		String originPw = mapper.selectPw(memberNo);
		
		// 비밀번호 불일치시 return 0;
		if(! bcrypt.matches( memberPw, originPw) )  return 0;
		
		// 비밀번호 일치시 
		return mapper.secession(memberNo);
	}


	// 파일 업로드 테스트1
	@Override
	public String fileUpload1(MultipartFile uploadFile) throws IllegalStateException, IOException {
		
		// MultipartFile이 제공하는 메서드
//			ㄴ getSize() : 파일 크기
//			ㄴ isEmpty() : 업로드한 파일이 없을 경우 true
//			ㄴ getOriginalFileName() : 원본 파일 명
//			ㄴ  transferTo(경로) : 메모리 또는 임시 저장 경로에 업로드된 파일을 원하는 경로에 전송
//									(서버 어떤 폴더에 저장할지 저장)
		
		// 업로드 한 파일이 없을 경우
		if( uploadFile.isEmpty()) return null;
		
		// 업로드 한 파일이 있을 경우 -> C:\\uploadFiles\\test\\파일명 으로 서버에 저장
		uploadFile.transferTo(new File("C:\\uploadFiles\\test\\" + uploadFile.getOriginalFilename() ));
		
		// 웹에서 해당 파일에 접근할 수 있는 경로 반환, ( 서버: C:\\uploadFiles\\test\\a.jpg
//															ㄴ> 웹 접근 주소 : /myPage/file/a.jpg (<~FileConfig에서 오버라이드한 내용)
		return "/myPage/file/" + uploadFile.getOriginalFilename();
	}


	
	
	/// +=============================
		/* DB에 파일 저장이 가능하나 선호 XX <- DB 부하
		 * 
		 * 1) DB에 서버에 저장할 파일 경로를 미리 저장 (지정해두면 나중에 주소를 보고 서버를 찾아감)
		 * 
		 * 2) DB에 삽입/수정 성공 후 서버에 파일을 저장 
		 * 
		 * 3) 파일 저장 실패시 -> 예외 발생 -> @Transactional을 이용해 rollback 수행
		 *  									 ㄴ> 예외 발생시에만 동작
		 * */	
	/// +==============================
	
	
	// 파일 업로드 테스트2 ( +DB )
	@Override
	public int fileUpload2(MultipartFile uploadFile, int memberNo) throws IllegalStateException, IOException {

		// 업로드된 파일이 없다면 == 선택된 파일이 없을 경우
		if(uploadFile.isEmpty()) return 0;
		

		
		// 1. 서버에 저장할 파일 경로 만들기
		String folderPath = "C:\\uploadFiles\\test\\"; // 파일이 저장될 서버 폴더 경로
		String webPath = "/myPage/file/"; // 클라이언트가 파일이 저장된 폴더에 접근할 수 있는 주소
		
		
		// 2. DB에 전달할 데이터를 DTO 으로 묶어서 INSERT 호출
		// webPath, memberNo, 원본 파일명, 변경된 파일명
		
		// 변경된 파일명
		String fileRename = Utility.fileRename(uploadFile.getOriginalFilename());
		
		// Builder 패턴을 이용해서 UploadFile 객체 생성
//			ㄴ 장점1) 반복되는 참조 변수명, set 구문 생략, 2)  method chaining을 이용해서 한 줄로 작성 가능
		UploadFile uf = UploadFile.builder().memberNo(memberNo).filePath(webPath).
				fileOriginalName(uploadFile.getOriginalFilename()).fileRename(fileRename).build();

		int result = mapper.insertUploadFile(uf);
		
		// 3. INSERT 성공시 파일을 지정된 서버 폴더에 저장
		
		if(result == 0) return 0; // 삽입 실패시
		
		// 성공시 -> C:\\uploadFiles\\test\\변경된파일명으로 파일을 서버에 저장
		uploadFile.transferTo(new File(folderPath + fileRename)); // <~ checkedException 발생 -> 예외처리 필수
		
		// @Transactional은 unCheckedException만 처리 -> rollbackFor 속성 이용, 롤백할 예외 범위 수정
		
		return result;
	}


	// 파일 목록 조회
	@Override
	public List<UploadFile> fileList() {
		// TODO Auto-generated method stub
		return mapper.fileList();
	}


	// 여러 파일 업로드
	@Override
	public int fileUpload3(List<MultipartFile> aaaList, List<MultipartFile> bbbList, int memberNo)
			throws IllegalStateException, IOException {

		// 1. aaaList 처리
		int result1 = 0;
		
		// 업로드 된 파일이 없을 경우 제외하고 업로드
		for(MultipartFile file : aaaList) {
			
			if(file.isEmpty()) continue; // 파일이 없으면 다음 파일로 넘어감
		
			// fileUpload2() 메서드 호출 -> 파일 한개 업로드 + DB에 INSERT
//			fileUpload2(file, memberNo);
			
			// Upload2 메서드 호출 -> -> 파일 한개 업로드 + DB에 INSERT
			
			result1 += fileUpload2(file, memberNo);
		}
		
		// 2. bbbList 처리
		int result2 = 0;
		
		// 업로드 된 파일이 없을 경우 제외하고 업로드
		for(MultipartFile file : bbbList) {
			
			if(file.isEmpty()) continue; // 파일이 없으면 다음 파일로 넘어감
		
			result2 += fileUpload2(file, memberNo);
		}
		return result1 + result2;
	}

	// 프로필 이미지 변경
	@Override
	public int profile(MultipartFile profileImg, Member loginMember) throws IllegalStateException, IOException {

		String updatePath = "";//"C:\\uploadFiles\\test\\";
		String rename = "";
		
		// 업로드한 이미지가 있을 경우
		if( !profileImg.isEmpty()) {
			
			// updatePath 조합
			rename = Utility.fileRename(profileImg.getOriginalFilename());
			
			// /myPage/profile/변경된파일명.jpg
			updatePath = profileWebPath + rename;
		}
		// 수정된 프로필 이미지 경로 + 회원 번호를 저장할 DTO 객체
		Member mem = Member.builder().memberNo(loginMember.getMemberNo()).profileImg(updatePath).build();
				
		// Update 수행
		int result = mapper.profile(mem);
		
		if(result > 0) { // 수정 성공시
			
			// 프로필 이미지를 없앤 경우 (NULL로 수정한 경우)를 제외-> 업로드한 이미지가 있을 경우
			if( !profileImg.isEmpty()) profileImg.transferTo(new File(profileFolderPath + rename)); // 파일을 서버 지정된 폴더에 저장				

			// 세션 회원 정보에서 프로필 이미지 경로를 업데이트한 경로로 변경
			loginMember.setProfileImg(updatePath);
		}
		return result;
	}

	

}
