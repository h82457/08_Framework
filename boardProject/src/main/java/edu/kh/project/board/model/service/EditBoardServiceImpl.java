package edu.kh.project.board.model.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import edu.kh.project.board.model.BoardInsertException;
import edu.kh.project.board.model.dto.Board;
import edu.kh.project.board.model.dto.BoardImg;
import edu.kh.project.board.model.mapper.EditBoardMapper;
import edu.kh.project.common.util.Utility;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
@PropertySource("classpath:/config.properties")  // config.properties 파일을 불러옴
public class EditBoardServiceImpl implements EditBoardService{

	private final EditBoardMapper mapper;
	
	/* config.properties 값을 얻어와 필드에 저장 */
	@Value("${my.board.web-path}")  // 게시글 이미지 요청 주소(DB 저장용)
	private String webPath;
	
	@Value("${my.board.folder-path}") // 게시글 이미지를 서버에 저장시에 사용하는 경로 (transferTo)
	private String folderPath;
	
	
	
	// 게시글 작성
	@Override
	public int boardInsert(Board inputBoard, List<MultipartFile> images) throws IllegalStateException, IOException {
		
		/* [1] 게시글에서 이미지를 제외한 글부분(inputBoard) 먼저 BOARD 테이블에 INSERT
				+ <select key> 태그 이용, INSERT 결과로 작성된 게시글 번호(생성된 시퀀스 번호) 반환 받기 */
		
		int result = mapper.boardInsert(inputBoard); // 매퍼에서 <select key>로 생성된 boardNo가 inputBoard에 저장된 상태(얕은 복사)
		
		if(result == 0) return 0; // <- 삽입 실패 (게시글 등록 실패)
		
		/* 게시글 INSERT 성공시 업로드 된 이미지가 실제로 존재할 경우 업로드 된 이미지만 별도로 저장, "BOARD_IMG"에 삽입하는 코드 작성 */
		int boardNo = inputBoard.getBoardNo(); // 삽입된 게시글의 번호를 변수로 저장
		
		List<BoardImg> uploadList = new ArrayList<>(); // 실제 업로드 된 이미지의 정보를 모아둘 List 생성
		
		
		// images 리스트에서 하나씩 꺼내서 선택된 파일이 있는지 검사 (isEmpty() 사용 불가)
		for(int i=0 ; i < images.size() ; i++) {
			
			// 실제 선택된 이미지 파일이 존재하는 경우
			if( !images.get(i).isEmpty() ) { 
				
//				// IMG_PATH == webPath
				// BOARD_NO == boardNo , IMG_ORDER == i(인덱스 == 순서)
				
				String originalName = images.get(i).getOriginalFilename(); // <- 원본명
				String rename = Utility.fileRename(originalName); // <- 변경명
				
				// 모든 값을 저장한 DTO 생성 (Builder 패턴 사용)
				BoardImg img = BoardImg.builder().imgOriginalName(originalName).imgRename(rename).
						imgPath(webPath).boardNo(boardNo).imgOrder(i).uploadFile(images.get(i)).build();
				
				uploadList.add(img); // <- for문에서 5바퀴를 반복하며 위에서 저장한 img 값으로 이미지 업로드
			}
		}
		
		// 선택한 이미지 파일이 없을 경우 이미지는 저장하지 않고 컨트롤러로 돌아감
		if( uploadList.isEmpty() ) return boardNo;

		
		// 선택한 이미지 파일이 존재할 경우 -> "BOARD_IMG" 테이블에 INSERT + 서버에 파일 저장
		
		/* 여러행 삽입 방법 */
//			1) 1행 삽입하는 SQL을 for문을 이용해서 여러번 호출
//			2) 여러행 삽입하는 SQL을 1회 호출 <~ 사용!
		
		result = mapper.insertUploadList(uploadList); // result == uploadList.size() == 삽입된 행의 개수
		
		// 다중 INSERT 성공 확인 (uploadList에 저장된 값이 모두 삽입 되었나)
		if(result == uploadList.size()) {
			
			// 서버에 파일 저장
			for(BoardImg img : uploadList) {
				img.getUploadFile().transferTo(new File( folderPath + img.getImgRename() )); // 임시로 실제 업로드 된 파일을 경로에 저장
			}
		}
		
		else { // 부분적으로 삽입 실패 -> 전체 서비스 실패로 판단 => 이전 삽입 내용 모두 롤백 진행
//					ㄴ> 롤백 방법 : 강제 RuntimeException 발생 (@Transactional)
			
			throw new BoardInsertException("이미지가 정상 삽입되지 않음");
		}
		
		
		return boardNo;
	}


	// 게시글 삭제
	@Override
	public int boardDelete(int boardNo) {
		
		return mapper.boardDelete(boardNo);
	}
}
