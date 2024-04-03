package edu.kh.project.myPage.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.myPage.model.mapper.MyPageMapper;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MyPageServiceImpl implements MyPageService{

	private final MyPageMapper mapper; // final 필드의 값을 의존성 주입 
	
	private final BCryptPasswordEncoder bcrypt;

	
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
		if(! bcrypt.matches((String)paramMap.get("currentPw"), originPw) ) { return 0; }
		
//		- 같을 경우 -> 새 비밀번호를 암호화 진행
		String encPw = bcrypt.encode((String)paramMap.get("newPw"));
		
		paramMap.put("currentPw", encPw);
		paramMap.put("memberNo", memberNo);
		
//		 -> 새 비밀번호로 변경(UPDATE)하는 Mapper 호출
//		    회원 번호, 새 비밀번호 -> 하나로 묶음 (Member 또는 Map) 
//		    -> 결과 1 또는 0 반환
		
		return mapper.updatePw(paramMap);
	}


}
