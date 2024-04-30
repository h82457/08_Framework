package com.project.pawlife.adoption.model.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.project.pawlife.adoption.model.dto.Adopt;
import com.project.pawlife.adoption.model.mapper.AdoptionMapper;
import com.project.pawlife.common.exception.AdoptInsertException;
import com.project.pawlife.common.util.Pagination;
import com.project.pawlife.common.util.Utility;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@PropertySource("classpath:/config.properties")
public class AdoptionServiceImpl implements AdoptionService {

	
	@Value("${my.adopt.web-path}")
	private String adoptWebPath; // /myPage/profile/

	@Value("${my.adopt.folder-path}")
	private String adoptFolderPath;
	
	@Autowired
	private final AdoptionMapper mapper;

	// 입양 게시글 작성
	@Override
	public int adoptionInsert(Adopt inputAdopt, MultipartFile thumnailImg,int memberNo){
		
		
		// 수정할 경로
		String updatePath = null;
		
		String rename = null;
		
		// 업로드한 이미지가 있을 경우
		if(!thumnailImg.isEmpty()) {
			
			// updatePath 조합
			
			// 파일명 조합
			rename = Utility.fileRename(thumnailImg.getOriginalFilename());

			// /myPage/profile/변경된파일명.jpg
			updatePath = adoptWebPath+rename;
			
			inputAdopt.setThumnail(updatePath);
			inputAdopt.setMemberNo(memberNo);
		}
				
		
		int result = mapper.adoptionInsert(inputAdopt);
		
		if(result > 0) {
			try {
				thumnailImg.transferTo(new File(adoptFolderPath + rename));
			} catch (Exception e) {
				e.printStackTrace();
				throw new AdoptInsertException("입양 게시글 이미지 삽입 중 예외 발생");
			}
		}
		
		
		return result;
		
		}
	
	@Override
	public Map<String, Object> selectAdoptList(int cp) {

		// 삭제 되지 않은 게시글 수 조회
		int listCount = mapper.getListCount();
		
		// Pagination 객체 생성
		// * Pagination 객체 : 게시글 목록 구성에 필요한 값을 저장
		Pagination pagination = new Pagination(cp, listCount);
		
		// 페이지 목록 조회
		
		/* ROWBOUND 객체 (MyBatis 제공 객체)
		 * 
		 * 지정된 크기(offset)만큼 건너뛰고
		 * 제한된 크기(limit) 만큼의 행을 조회하는 객체
		 * 
		 * */
		
		int limit = pagination.getLimit();
		int offset = (cp-1)*limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		
		List<Adopt> adoptList = mapper.selectAdoptList(rowBounds);
		
		// 목록 조회결과 Pagination 객페를 Map으로 묶음
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("pagination", pagination);
		map.put("adoptList", adoptList);
		
		
		// 결과 반환
		return map;
	}
	
	// 게시글 상세 조회
	@Override
	public Adopt selectOneAdopt(Map<String, Integer> map) {
		
		
		
		return mapper.selectOneAdopt(map);
	}

	// 북마크 체크/ 해제
	@Override
	public int bookCheck(Map<String, Integer> map) {
		
		int result =0;
		
	   if(map.get("bookmark") == 1) { // bookmarkcheck 테이블에 delete
			
			result = mapper.deleteBookCheck(map);
	
		}
	   // 북마크가 해제된 상태인 경우 bookMark ==0
	   
	   else {
		   result = mapper.insertBookCheck(map);
	   }
		
		
		return result;
	}
	@Override
	public int adoptUpdate(Adopt adoptInput,MultipartFile thumnailImg,int statusCheck) {
	
			// 수정할 경로
			String updatePath = null;
			
			String rename = null;
			
			if(statusCheck==-1) {
				adoptInput.setThumnail("none");
			}
			
			// 업로드한 이미지가 있을 경우
			if(!thumnailImg.isEmpty()) {
				
				// updatePath 조합
				
				// 파일명 조합
				rename = Utility.fileRename(thumnailImg.getOriginalFilename());

				// /myPage/profile/변경된파일명.jpg
				updatePath = adoptWebPath+rename;
				
				adoptInput.setThumnail(updatePath);
			}
			
			
			int result = mapper.adoptUpdate(adoptInput);
			
			if(result > 0) {
				try {
					thumnailImg.transferTo(new File(adoptFolderPath + rename));
				} catch (Exception e) {
					e.printStackTrace();
					throw new AdoptInsertException("입양 게시글 이미지 삽입 중 예외 발생");
				}
			}
			
			
			return result;
			
	}
	
	/** 게시글 삭제
	 *
	 */
	@Override
	public int adoptDelete(Map<String, Integer> map) {
		return mapper.adoptDelete(map);
	}
	
	
}
