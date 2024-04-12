package edu.kh.project.common.interceptor;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import edu.kh.project.board.model.service.BoardService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/* interceptor : 요청/응답 가로채는 객체 (Spring 지원)
 * 
 * Client <-> Filter <-> Dispatcher Servlet <-> Interceptor <-> Contorller ...
 * 
 * * >HandlerInterceptor 인터페이스< 상속 받아 구현 필요.
 * 
 * - preHandle (전처리) : Dispatcher Servlet -> Contorller 사이 수행 (컨트롤러 이전) <- 전처리를 주로 사용
 * 
 * - postHandle (후처리) : Contorller -> Dispatcher Servlet 사이 수행 (컨트롤러 수행 후 서블릿으로 돌아오기 전)
 * 
 * - afterCompletion (뷰 완성, forward 코드 해석 후) : View Resolver -> Dispatcher Servlet 사이 수행 <- 로그 남기는등 거의 사용하지 않음
 * */

@Slf4j
// @RequiredArgsConstructor // BoardService 를 호출하기 위함
public class BoardTypeInterceptor implements HandlerInterceptor{ // defult는 오버라이딩 강제 아니나 사용을 위해 오버라이딩

	// BoardService 의존성 주입
	@Autowired
	private /*final*/ BoardService service;
	
	
	//
	// 전처리: 서블릿 -> 컨트롤러로 가는 요청을 가로채서 서비스 - 매퍼로 전달 (가로채서 원래의 서비스로 가지않음)
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) // <- 요청
			throws Exception {
		
		// application scope 객체 얻어오기
//			ㄴ> 서버 종료시까지 유지되는 Servlet 내장 객체, 서버 내 딱 한개 존재 - 모든 클라이언트가 공용으로 사용
		
		 ServletContext application = request.getServletContext();
		 
		 
		 // application scope 범위에 "boardTypeList"가 없을 경우, 최초 접속시
		 if(application.getAttribute("boardTypeList") == null) {
			 
			 log.info("BoardTypeInterceptor - postHandle(전처리) 동작 실행");
			 
			 // boardTypeList 조회 서비스 호출
			 List<Map<String, Object>> boardTypeLit = service.selectBoardTypeList();
			 
			 // 조회 결과를 application scope에 추가
			 application.setAttribute("boardTypeList", boardTypeLit);
		 }
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	// 후처리
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception { // 요청에 사용하는것  + Model (modelview는 응답할때만 사용)
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	} 

}
