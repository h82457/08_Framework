package edu.kh.project.member.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.kh.project.member.model.dto.Member;
import edu.kh.project.member.model.mapper.MemberMapper;

@Transactional // < - 해당 클래스 메서드 종료시까지 예외(RuntimeException)가 발생하지 않으면 commit / 발생시 rollback (AOP 기반 기술)


@Service // 비즈니스 로직 처리 역할 + 빈 등록
public class MemberServiceImpl implements MemberService{

	@Autowired // 등록된 bean 중에서 같은 타입 또는 상속관계인 빈을 자동으로 의존성 주입
	private MemberMapper mapper;
	
	// BCrypt 암호화 객체 의존성 주입(securityConfig 참고)
	@Autowired
	private BCryptPasswordEncoder bcrypt;

	// 로그인 서비스
	@Override
	public Member login(Member inputMember) {
		
		// 테스트 (디버그 모드)
//		String bcryptPassword = bcrypt.encode(inputMember.getMemberPw());
//			ㄴ bcrypt.encode(문자열) : 문자열을 암호화하여 반환 
		
		
		// 1) 이메일이 일치하면서 탈퇴하지 않은 회원 조회
		Member loginMember = mapper.login(inputMember.getMemberEmail());
		
		
		// 2) 일치하는 이메일이 없어서 조회 결과가 null인 경우
		if(loginMember == null)	return null;
		
		
		// 3) 입력 받은 비밀번호 (inputMember.getMemberPw() (평문))와
//				암호화 된 비밀번호(loginMember.getMemberPw()) 두 비밀번호가 일치하는지 확인
//		 					ㄴ> 이메일은 일치, 비밀번호가 일치하지 않는 경우
		
		if ( !bcrypt.matches(inputMember.getMemberPw(), loginMember.getMemberPw()) ) return null;
		
		
		// 로그인 결과에서 비밀번호 제거
		loginMember.setMemberPw(null);
		
		return loginMember;
	}

	@Override
	public int signup(Member inputMember, String[] memberAddress) {
		
		// 주소가 입력되지 않으면 inputMember.getMemberAddress() -> ",," /  memberAddress -> [,,]
		
		// 주소가 입력된 경우
		if(!inputMember.getMemberAddress().equals(",,")) {
			
			// String.join("구분자", 배열) -> 배열의 모든 요소 사이에 구분자를 추가, 하나의 문자열로 만드는 메서드
			String address = String.join("^^^", memberAddress);
//			 ㄴ> 구분자로 "^^^" 쓴 이유 : 주소, 상세주소에 없는 특수문자 작성-> 나중에 다시 3분할시 구분자로 사용 예정
			
			inputMember.setMemberAddress(address); // <- mybatis는 매개변수 1개만 가능, inputMember 주소로 합쳐진 주소를 세팅
		
		} else { // 주소입력 x
			
			inputMember.setMemberAddress(null);
		}
		
		// 비밀번호 암호화 - inputMember에 세팅
		String encPw = bcrypt.encode(inputMember.getMemberPw());
		inputMember.setMemberPw(encPw);
		
		// 회원가입 매퍼 메서드 호출 -> Mybatis에 의해 자동으로 SQL 수행
//				(매퍼 메서드 호출시 SQL에 사용할 파라미터는 1개만 가능)
		return mapper.signup(inputMember);
	}

	// 이메일 중복 검사
	@Override
	public int checkEmail(String memberEmail) { return mapper.checkEmail(memberEmail); }

	// 닉네임 중복 검사
	@Override
	public int checkNickname(String memberNickname) {
		
		return mapper.checkNickname(memberNickname);
	}
}
















/* BCrypt 암호화 (Spring Security 제공)
 * 
 * 입력된 문자열(비밀번호)에 salt를 추가한 후 암호화 -> 암호화 할 때 마다 결과가 다름
 * ex) 1234 입력 -> $12!asdfg 
 * ex) 1234 다시 입력 -> $12!qwer
 * 
 * -- 비밀번호 확인 방법
 * 	BCryptPasswordEncoder.metches(평문 비밀번호, 암호화된 비밀번호)
 * 		ㄴ> 평문 비밀번호와 암호화된 비밀번호가 같은 경우 true, 아니면 false 반환
 * 
 * 로그인 / 비밀번호 변경 / 탈퇴 등 비밀번호가 입력외는 경우 DB에 저장된 암호화된 비밀번호를 조회해서 matches() 메서드로 비교
 */