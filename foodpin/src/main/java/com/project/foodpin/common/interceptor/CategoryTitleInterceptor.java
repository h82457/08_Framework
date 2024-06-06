package com.project.foodpin.common.interceptor;

import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
                                                    // HandlerInterceptor 상속 받기
public class CategoryTitleInterceptor implements HandlerInterceptor{

	// 후처리 (Controller ->Dispatcher Servlet 사이)
	@Override 
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		// application scope에서 categoryTypeList 얻어오기
		ServletContext application = request.getServletContext();
		
		// boardTypeList
		// [{boardCode:1, boardName=공지}, {boardCode:2, boardName=자유},....]
		// List 안의 각각의 요소가 Map으로 저장되어 있다 
		
		// boardTypeList 얻어오는 코드
		List<Map<String, Object>> categoryTypeList = 
				(List<Map<String, Object>>)application.getAttribute("categoryTypeList");
		
		log.debug(categoryTypeList.toString());
		
		//log.debug("boardCode: " + request.getAttribute("boardCode")); -> 안됨
		
		// Uniform Resource Identifier : 통합 자원 식별자
		// - 자원 이름 (주소) 만 봐도 무엇인지 구별할 수 있는 문자열
		
		//uri : / board/1 - 한 프로그램 안에서 구분할 수 있는 문자열
		// url : localhost/board/1 - 위에서 주소까지 들어감
		String uri = request.getRequestURI();
		
		//log.debug("uri : "+ uri); 
		// 위의 요청 주소(/board/2) 뒤에 보드 코드를 잘라서  
		//보드 네임을 얻어와 세팅할 코드를 만들 것임
		
		
		try {
		
															// [" ", "board", "1"]
		int categoryCode = Integer.parseInt(uri.split("/")[2]); // String을 int로 바꿈
		
		// boardTypeList 에서 boardCode를 하나씩 꺼내어 비교
		for( Map<String, Object> categoryType : categoryTypeList) {
			
			// Object를 String으로 바꾸고 또 int로 바꿔서 비교할 것임
			
			// String.valueOf(값) : String 으로 변환
			
			int temp=
			Integer.parseInt( String.valueOf( categoryType.get("categoryCode")));
			
			//비교 결과가 같다면
			// request scope에 boardName을 추가
			if(temp == categoryCode) {
				request.setAttribute("categoryTitle", categoryType.get("categoryTitle"));
				
				break;
			}
		}
		
		}catch(Exception e) {
			// 오류 나도 그냥 넘김
		}
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	
	
}
