package com.project.pawlife.adoption.model.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.project.pawlife.adoption.model.dto.Adopt;
import com.project.pawlife.adoption.model.mapper.AdoptionMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
@PropertySource("classpath:/config.properties")
public class AdoptionServiceImpl implements AdoptionService {

	
	@Value("${my.profile.web-path}")
	private String profileWebPath; // /myPage/profile/

	@Value("${my.profile.folder-path}")
	private String profileFolderPath;
	
	@Autowired
	private final AdoptionMapper mapper;

	// 입양 게시글 작성
	@Override
	public int adoptionInsert(Adopt inputAdopt, MultipartFile thumnailImg){
		
		
		// 수정할 경로
		String updatePath = null;
		
		String rename = null;
		
		// 업로드한 이미지가 있을 경우
		if(!thumnailImg.isEmpty()) {
			
			// updatePath 조합
			
			// 파일명 조합
			rename = com.project.pawlife.common.util.Utility.fileRename(thumnailImg.getOriginalFilename());

			// /myPage/profile/변경된파일명.jpg
			updatePath = profileWebPath+rename;
			
			inputAdopt.setThumnail(updatePath);
		}
				
		
		int result = mapper.adoptionInsert(inputAdopt);
		
		if(result > 0) {
			try {
				thumnailImg.transferTo(new File(profileFolderPath + rename));
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("고쳐요");
			}
		}
		
		
		return result;
		
		}
}
