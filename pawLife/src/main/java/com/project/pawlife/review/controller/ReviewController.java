package com.project.pawlife.review.controller;

<<<<<<< HEAD
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
=======
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> c28837a1946e008b1ff30a17a628d52b56b6ac71
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
<<<<<<< HEAD
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
=======
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
>>>>>>> c28837a1946e008b1ff30a17a628d52b56b6ac71

import com.project.pawlife.adoption.model.dto.Adopt;
import com.project.pawlife.member.model.dto.Member;
import com.project.pawlife.review.model.dto.Review;
import com.project.pawlife.review.model.service.ReviewService;

<<<<<<< HEAD
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
=======
>>>>>>> c28837a1946e008b1ff30a17a628d52b56b6ac71
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("review")
public class ReviewController {

	private final ReviewService service;
	
<<<<<<< HEAD
	/* config.properties 값을 얻어와 필드에 저장 */
	@Value("${my.board.web-path}")  // 게시글 이미지 요청 주소(DB 저장용, 서버 주)
	private String webPath;
	
	@Value("${my.board.folder-path}") // 게시글 이미지를 서버에 저장시에 사용하는 경로 (transferTo)
	private String folderPath;
	
=======
>>>>>>> c28837a1946e008b1ff30a17a628d52b56b6ac71

	
	// 리뷰 게시판 리스트로 이동
	@GetMapping("reviewList")
	public String reviewPage(@RequestParam(value="cp",required=false, defaultValue="1") int cp, Model model) { 
		
		Map<String, Object> map = service.selectReviewList(cp);
		
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("reviewList", map.get("reviewList"));
		
		return "review/reviewList";
	}
	

	// 후기 상세 페이지 이동
	@GetMapping("reviewDetail")
	public String reviewDetail() {
		return "review/reviewDetail";
	}
	
	
	/** 리뷰 게시글 상세 조회
	 * @return
	 */
	@GetMapping("reviewList/{reviewNo:[0-9]+}")
	public String reviewDetail(@PathVariable("reviewNo") int reviewNo, Model model, RedirectAttributes ra) {
		
		Map<String, Integer> map = new HashMap<>();
		map.put("reviewNo",reviewNo);
		
		Review review = service.selectOneReview(map);
		
		String path = null;
		
		if(review == null) {
			
			path = "redirect:/review/reviewList";
			ra.addFlashAttribute("message","게시글이 존재하지 않습니다.");
		}
		else {
			path = "review/reviewDetail";
			model.addAttribute("review", review);
		}
		
		return path;
	}
	

	// 후기 글쓰기화면으로 이동
	@GetMapping("reviewWrite")
	public String reviewWrite() { return "review/reviewWrite"; }
	
	
	
//	@ResponseBody
//	@PostMapping("imageUpload")
//	public void communityImageUpload(HttpServletRequest req, HttpServletResponse resp, MultipartHttpServletRequest multiFile) throws Exception{
//	    PrintWriter printWriter = null;
//	    OutputStream out = null;
//	    MultipartFile file = multiFile.getFile("upload");
//	    
//	    
//	    if(file != null) {
//			if (file.getSize() > 0 /* && StringUtils.isNotBlank(file.getName()) */) {
//	            if(file.getContentType().toLowerCase().startsWith("image/")) {
//	                try{
//	                    //String fileName = file.getOriginalFilename(); // fileOriginalName
//	                    String fileOriginalName = file.getOriginalFilename(); // fileOriginalName
//
//	                    byte[] bytes = file.getBytes();
//	                    
//	                    String uploadPath = folderPath;
//
//	                    File uploadFile = new File(uploadPath);
//
//	                    if(!uploadFile.exists()) {
//	                        uploadFile.mkdir();
//	                    }
//
//	                   // String fileName1 = UUID.randomUUID().toString();// fileRename
//	                    String fileRename = UUID.randomUUID().toString();// fileRename
//	                    
//	                    
//	                    uploadPath = uploadPath + "/" + fileRename +fileOriginalName;
//				            
//	                    out = new FileOutputStream(new File(uploadPath));
//	                    out.write(bytes);
//				            
//	                    printWriter = resp.getWriter();
//						String fileFullpath = req.getContextPath() + "/resources/ckeditorUpload/"
//								+ fileRename/* +fileOriginalName */; //url경로(fileUrl)
//				            
//	                    JsonObject json = new JsonObject();
//				            
//	                    json.addProperty("uploaded", 1);
//	                    json.addProperty("fileName", fileOriginalName);
//	                    json.addProperty("url", fileFullpath);
//				          
//	                    printWriter.print(json);
//	                    System.out.println(json);
//				 
//	                }catch(IOException e){	
//	                    e.printStackTrace();
//	                } finally {
//				        	
//	                    if (out != null) {
//	                        out.close();
//	                     }
//	                     if (printWriter != null) {
//	                         printWriter.close();
//	                     }
//	                 }
//	              } 
//	          }
//	      }
//	 }

	
	@PostMapping("imageUpload") 
	public void imageUpload(HttpServletRequest request, HttpSession session,
			HttpServletResponse response, MultipartHttpServletRequest multiFile , 
			@RequestParam MultipartFile upload) throws Exception{ 
		
		// 랜덤 문자 생성 
		UUID uid = UUID.randomUUID();
		
		OutputStream out = null; 
		PrintWriter printWriter = null; 
		
		//인코딩 
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		try{ 
			//파일 이름 가져오기 
			String fileName = upload.getOriginalFilename();
			byte[] bytes = upload.getBytes(); 
			
			//이미지 경로 생성 
			String real = session.getServletContext().getRealPath("/resources/tripPhoto");
			String ckUploadPath = real + "/" + uid + "_" + fileName; 
			File folder = new File(real);
			
			//해당 디렉토리 확인 
			if(!folder.exists()){ 
				try{ folder.mkdirs(); // 폴더 생성 
				}catch(Exception e){ 
					e.getStackTrace(); 
					} 
				}
			
			out = new FileOutputStream(new File(ckUploadPath)); 
			out.write(bytes); 
			out.flush(); // outputStram에 저장된 데이터를 전송하고 초기화 
			
			String callback = request.getParameter("CKEditorFuncNum"); 
			printWriter = response.getWriter(); 
			String fileUrl = "ckImgSubmit.do?uid=" + uid + "&fileName=" + fileName; // 작성화면 
			
			// 업로드시 메시지 출력
			printWriter.println("{\"filename\" : \""+fileName+"\", \"uploaded\" : 1, \"url\":\""+fileUrl+"\"}"); 
			printWriter.flush(); 
			
		}catch(IOException e){ 
			e.printStackTrace(); 
			} finally { 
				try { if(out != null) { out.close(); } 
				if(printWriter != null) { printWriter.close(); } 
				} catch(IOException e) { e.printStackTrace(); } 
			} 
		return; 
	}
	
	@RequestMapping(value="imageUpload") 
	public void ckSubmit(@RequestParam(value="uid") String uid 
			, @RequestParam(value="fileName") String fileName
			, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{ 
		
		//서버에 저장된 이미지 경로 
		String real = session.getServletContext().getRealPath("/resources/tripPhoto");
		String sDirPath = real+ "/" + uid + "_" + fileName; 
		File imgFile = new File(sDirPath); 
		
		//사진 이미지 찾지 못하는 경우 예외처리로 빈 이미지 파일을 설정한다. 
		if(imgFile.isFile()){ byte[] buf = new byte[1024]; 
		int readByte = 0; 
		int length = 0; 
		byte[] imgBuf = null; 
		
		FileInputStream fileInputStream = null; 
		ByteArrayOutputStream outputStream = null; 
		ServletOutputStream out = null; 
		
		try{ 
			fileInputStream = new FileInputStream(imgFile); 
			outputStream = new ByteArrayOutputStream(); 
			out = response.getOutputStream(); 
			
			while((readByte = fileInputStream.read(buf)) != -1){ 
				outputStream.write(buf, 0, readByte); 
				} 
			
			imgBuf = outputStream.toByteArray(); 
			length = imgBuf.length; 
			out.write(imgBuf, 0, length); 
			out.flush(); 
			
		}catch(IOException e){ 
			e.getMessage();
		}finally { 
			outputStream.close();
			fileInputStream.close();
			out.close();
			} 
		} 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/** 후기 글 작성 (로그인 세션 등록시 회원번호 추가해서 insert 진행 수정)
	 * @param map
	 * @return
	 */
	@PostMapping("reviewWrite")
	public String reviewWrite(Review inputReivew, @RequestParam("thumnailImg") MultipartFile thumnailImg,
			@SessionAttribute("loginMember") Member loginMember, Model model) {

		int memberNo = loginMember.getMemberNo();
		
		int result = service.reviewWrite(inputReivew, thumnailImg, memberNo);
		
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
		
		return "redirect:/review/" + path;
	}
<<<<<<< HEAD
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/** 후기 상세 페이지
	 * @return
	 */
	@GetMapping("reviewDetail")
	public String reviewDetail() { return "review/reviewDetail"; }
=======

	
>>>>>>> c28837a1946e008b1ff30a17a628d52b56b6ac71
	
	/** 후기 수정 페이지 
	 * @return
	 */
	@GetMapping("reviewUpdate")
	public String reviewUpdate() { return "review/reviewUpdate"; }

	
	
}
