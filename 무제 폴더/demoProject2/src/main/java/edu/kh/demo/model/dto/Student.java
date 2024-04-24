package edu.kh.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// Spring EL 같은 경우 getter 작성 필수!!!
// -> ${Student.getName()} == ${Student.name} (get이 생략되었으나 실제로는 사용됨)

//	getter 대신 필드명 호출 형식으로 작성 -> 자동으로 getter를 호출

@Getter   // 컴파일시 getter 코드 자동 추가
@Setter   // 컴파일시 setter 코드 자동 추가
@ToString // 컴파일시 toString() 메서드가 자동 오버라이딩화 추가

@NoArgsConstructor	// 매개변수 없는 생성자(== 기본 생성자)
@AllArgsConstructor // 모든 필드를 초기화하는 용도의 매개변수 생성자

public class Student {
	
	private String studentNo;
	private String name;
	private int age;
	
//	public Student() {} <- 이미 @NoArgsConstructor 사용으로 추가되어 작성시 중복 오류
}
