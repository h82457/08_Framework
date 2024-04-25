package edu.kh.project.myPage.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
 @Builder // <- 빌더 패턴을 이용, 객체 생성/초기화를 쉽게 진행
//		기본 생성자가 생성X, MyBatis 조회 결과 담을떄 필요한 객체 생성 실패(MyBatis는 기본 생성자로 객체를 만들기 떄문
@NoArgsConstructor
@AllArgsConstructor
public class UploadFile {

	private int fileNo;
	private String filePath;
	private String fileOriginalName;
	private String fileRename;
	private String fileUploadDate;
	private int memberNo;
	
	private String memberNickname;
	
}
