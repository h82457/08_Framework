package com.project.pawlife.review.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.pawlife.review.model.dto.Review;
import com.project.pawlife.review.model.dto.UploadFile;
import com.project.pawlife.review.model.service.ReviewService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("review")
public class ReviewController {

	@Autowired
	private final ReviewService service;
	
	/* config.properties 값을 얻어와 필드에 저장 */
	@Value("${my.board.web-path}")  // 게시글 이미지 요청 주소(DB 저장용)
	private String webPath;
	
	@Value("${my.board.folder-path}") // 게시글 이미지를 서버에 저장시에 사용하는 경로 (transferTo)
	private String folderPath;
	

	
	// 리뷰 게시판 리스트로 이동
	@GetMapping("reviewList")
	public String reviewPage() { return "review/reviewList"; }
	
	
	// 후기 글쓰기화면으로 이동
	@GetMapping("reviewWrite")
	public String reviewWrite() { return "review/reviewWrite"; }
	
	
	/** 후기 글 작성 (로그인 세션 등록시 회원번호 추가해서 insert 진행 수정)
	 * @param map
	 * @return
	 */
	@PostMapping("reviewWrite")
	public String reviewWrite(Review inputReivew, Model model) {

		// 추가할것) boardCode, 로그인한 회원 번호 inputReivew에 세팅 <- pathVariable, sessionattribute

		int result = service.reviewWrite(inputReivew);
		
		String path = "";
		String message = "";
		
		if(result > 0) {
			path = "reviewList"; // reviewDetail 구현 전까지 임시로 포워드하는 경로
			message = "후기글 등록이 완료되었습니다.";
			
		} else {
			path = "reviewWrite";
			message = "후기글 등록이 실패되었습니다.";
		}
		
		model.addAttribute("message", message);
		
		return "redirect:" + path;
	}
	
	/** 이미지 저장
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="ImageUpload")
	public void smarteditorMultiImageUpload(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		
		
		String sFileInfo = ""; // 에디터에 뿌려질 이미지의 주소 정보를 가지고 있는 변수
		String originalName = req.getHeader("file-name"); // <- 원본명
		String rename = com.project.pawlife.common.util.Utility.fileRename(originalName); // <- 변경명
		
		//확장자 체크 이미지 검증 배열변수: sFilenameExt <-비교-> 확장자: sFilenameExt
		String sFilenameExt = originalName.substring(originalName.lastIndexOf(".")+1); //파일 확장자
		sFilenameExt = sFilenameExt.toLowerCase(); //확장자를소문자로 변경
		String[] allowFileArr = {"jpg","png","bmp","gif"}; //이미지 검증 배열변수

		// [1] 이미지 검증
		int nCnt = 0;
		for(int i=0; i<allowFileArr.length; i++) {
			if(sFilenameExt.equals(allowFileArr[i])){
				nCnt++;
			}
		}
		//이미지가 아니라면
		if(nCnt == 0) {
			PrintWriter print = resp.getWriter();
			print.print("NOTALLOW_"+originalName);
			print.flush();
			print.close();
		} else {
			
			// [2] 디렉토리 설정
		
			//파일경로
			String filePath = folderPath; // <- 진짜 이미지가 저장되는 저장소 경로
			File file = new File(filePath); // 경로 생성위해 파일타입 변수 생성
	
			if(!file.exists()) file.mkdirs(); // 이미지 저장될 경로가 없으면 생성
			
			// [3] 폴더패스에 이미지 저장
			String fullPath = filePath + rename; //  <- 폴더패스에 리네임 덧붙여진 경로 (진짜 파일이 저장될 풀경로)
			
			
			InputStream inputStream = req.getInputStream(); // 서버에서 사진 받아오기
			OutputStream outputStream=new FileOutputStream(fullPath); // 진짜 파일이 저장될 풀경로로 저장
			
			int numRead;
			byte bytes[] = new byte[Integer.parseInt(req.getHeader("file-size"))];
			while((numRead = inputStream.read(bytes,0,bytes.length)) != -1){
					outputStream.write(bytes,0,numRead);
			}
			if(inputStream != null) {
				inputStream.close();
			}
			outputStream.flush();
			outputStream.close();
			
			// [4] 에디터에 이미지 뿌려주기 정보 출력
			sFileInfo += "&bNewLine=true";
			// img 태그의 title 속성을 원본파일명으로 적용시켜주기 위함
			sFileInfo += "&sFileName="+originalName;
			sFileInfo += "&sFileURL="+webPath+rename;
			PrintWriter printWriter = resp.getWriter();
			printWriter.print(sFileInfo);
			printWriter.flush();
			printWriter.close();
		
			// 메퍼에 보낼 데이터 DTO로 묶음
			UploadFile img = UploadFile.builder().filePath(webPath).fileOriginalName(originalName).
					fileRename(rename).build();
			
			int result = service.ImageUpload(img);
			
			
			
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/** 후기 상세 페이지
	 * @return
	 */
	@GetMapping("reviewDetail")
	public String reviewDetail() {
		return "review/reviewDetail";
	}
	
	/** 후기 수정 페이지
	 * @return
	 */
	@GetMapping("reviewUpdate")
	public String reviewUpdate() {
		return "review/reviewUpdate";
	}

	
}
