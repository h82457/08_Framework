package edu.kh.demo.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Lombok 라이브러리 이용
// 	ㄴ> 자주 사용하는 코드를 자동완성 (DTO-기본생성자, getter/setter, toString, Log)

@NoArgsConstructor // 기본 생성자 자동완성
@Getter // getter 자동 완성
@Setter // setter 자동 완성
@ToString // toString 오버라이딩 자동 완성
public class MemberDTO {

	private String memberId;
	private String memberPw;
	private String memberName;
	private int memberAge;

	
}
