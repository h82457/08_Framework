package edu.kh.demo.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import edu.kh.demo.model.dto.MemberDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


// Bean : 스프링이 만들고 관리하는 객체

@Controller // 요청/응답 제어 역할 명시 + Bean 등록

@RequestMapping("param") // /parma 으로 시작하는 요청을 현재 컨트롤러로 매핑

@Slf4j // log를 이용한 메시지 출력시 사용(Lombok 제공)
public class parameterController {
	
	@GetMapping("main") // /param/main GET 방식으로 요청 매핑
	public String paramMain() {
		
		
//		classpath:\resources\templates\param\param-main.html
		return "param/param-main";
	}
	
	
	/* 1. HttpServletRequest.getParameter("Key") 이용 */
//			ㄴ> HttpServletRequest : 요청 클라이언트 정보, 제출된 파라미터등을 저장한 객체, 클라이언트 요청시 생성
	
//		Spring의 Controller 메서드 작성시 매개변수에 원하는 객체를 작성 -> 존재하는 객체를 바인딩 / 없으면 생성해서 바인딩
//			--> ArgumentResolver(전달인자 해결사)
	@PostMapping("test1") // /param/test1	POST 방식 요청 매핑
	public String paramTest1(HttpServletRequest req) {
		
		String inputName = req.getParameter("inputName");
		String inputAdress = req.getParameter("inputAdress");
		int inputAge = Integer.parseInt( req.getParameter("inputAge") );
		
		log.debug("inputName" + inputName); // debug : 코드 오류 해결 <~ 코드 오류X, 정상 수행X, 값이 잘못된 경우 값 추적
		log.debug("inputAdress" + inputAdress);
		log.debug("inputAge" + inputAge);
		
		/* Spring) Redirect(재요청) : Controller 메서드 반환값에 "redirect:요청주소"; 작성*/
		return "redirect:/param/main";
	}
	

	/* 2. @RequestParam 어노테이션 - 낱개(한 개, 단 수)개 파라미터 얻어오기
	 * 
	 * - request객체를 이용한 파라미터 전달 어노테이션 
	 * - 매개 변수 앞에 해당 어노테이션을 작성하면, 매개변수에 값이 주입됨.
	 * - 주입되는 데이터는 매개 변수의 타입이 맞게 형변환/파싱이 자동으로 수행됨!
	 * 
	 * [기본 작성법]
	 * @RequestParam("key") 자료형 매개변수명
	 * 
	 * 
	 * [속성 추가 작성법]
	 * @RequestParam(value="name", required="fasle", defaultValue="1") 
	 * 
	 * value : 전달 받은 input 태그의 name 속성값
	 * 
	 * required : 입력된 name 속성값 파라미터 필수 여부 지정(기본값 true) 
	 * 	-> required = true인 파라미터가 존재하지 않는다면 400 Bad Request 에러 발생 
	 * 	-> required = true인 파라미터가 null인 경우에도 400 Bad Request
	 * 
	 * defaultValue : 파라미터 중 일치하는 name 속성 값이 없을 경우에 대입할 값 지정. 
	 * 	-> required = false인 경우 사용
	 */
	
	@PostMapping("test2")
	public String paramTest2(@RequestParam("title") String title
						, @RequestParam("writer") String writer
						, @RequestParam("price") int price
						, @RequestParam(value="publisher", required = false, defaultValue = "교보문고") String publisher) { // <- int 자동으로 파싱
		
//				(type=Bad Request, status=400). Required parameter 'publisher' is not present. ~> 파라미터 불충분에러 
		
		log.debug("title : " + title);
		log.debug("writer : " + writer);
		log.debug("price : " + price);
		log.debug("publisher : " + publisher);
		
		return "redirect:/param/main";
	}
	
	
	/* 3. @RequestParam 어노테이션을 이용 - 여러개 파라미터 */
	
	// String[] , List<자료형> , Map<String, Object> (defaultValue 속성 사용 불가)
	
	@PostMapping("test3")
	public String paramTest3(
			@RequestParam(value="color", required=false) String[] colorArr,
			@RequestParam(value="frute", required=false) List<String> fruitList,
			@RequestParam Map<String, Object> pramMap
			) {
		log.debug("colorArr : " + Arrays.toString(colorArr)); // java.util import
		log.debug("frute : " + fruitList);
		log.debug("pramMap : " + pramMap);
//		@RequestParam Map<String, Object> : 제출된 모든 파라미터가 Map에 저장, 문제점 : key(name 속성값) 중복시 덮어쓰기 실행
//			-> 같은 name 속성 파라미터가 String[], List로 저장 X
		
		
		return "redirect:/param/main";
	}
	
	
	/* 4. @ModelAttribute를 이용한 파라미터 얻어오기 */
	
	// @ModelAttribute
	
	// - DTO(또는 VO)와 같이 사용하는 어노테이션
	
	// - 전달 받은 파라미터의 name 속성 값이 같이 사용되는 DTO의 필드명과 같다면
	//   자동으로 setter를 호출해서 필드에 값을 세팅
	
	// *** @ModelAttribute 사용 시 주의사항 ***
	// - DTO에 기본 생성자, sette가 필수로 존재해야 한다!
	
	// *** @ModelAttribute 어노테이션은 생략이 가능하다! ***
	
	// *** @ModelAttribute를 이용해 값이 필드에 세팅된 객체를 "커맨드 객체" 라고 한다 ***
	
	@PostMapping("test4")
	public String paramTest4(/* @ModelAttribute */ MemberDTO inputMember) {
//									ㄴ> 커맨드 객체
		// Lombok 테스트
		MemberDTO mem = new MemberDTO();
		
		mem.getMemberAge(); // getter
		mem.setMemberAge(0); // setter 
//		 ㄴ 안만들었는데 어노테이션을 통해 자동 생성되어 호출 가능
		
		log.debug("inputMember :" + inputMember.toString());
		
		
		return "redirect:/param/main";
	}
	
	
	
	
	
	
	
	
	
}
