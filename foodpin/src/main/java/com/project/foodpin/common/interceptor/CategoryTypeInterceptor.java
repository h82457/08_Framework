package com.project.foodpin.common.interceptor;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.project.foodpin.main.model.service.MainService;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * Interceptor : 가로채다 // 요청 / 응답을 가로채는 객체 (Spring 지원)
 * 
 * Dispatcher Servlet과 contoller
 * 
 *  // 모든 요청을 처리하기 위해 사용
 *  
 * Client 요청 <->Filter(요청과 응답을 걸러줌)<-> Dispatcher Servlet <->Interceptor<-> Controller
 * 
 * HandlerInterceptor 인터페이스를 상속 받아서 구현 해야한다
 * 
 * - preHandler (전처리) : Dispatcher Servlet -> Controller 사이 수행
 *                              //Controller가 역할을 수행하기 전에 처리 ( 보통 많이 사용)
 * 
 * - postHandler (후처리) : Controller -> Dispatcher Servlet 사이 수행
 * 
 * - afterCompletion (뷰 완성(forward 코드 해석) 후)
 *                          : View Resolver -> Dispatcher Servlet 사이 수행
 * 
 * */
@Slf4j
@RequiredArgsConstructor
public class CategoryTypeInterceptor implements HandlerInterceptor{

	// MainService 의존성 주입 받기
	@Autowired
	private MainService service;
	
	
	
	//alt + shift + s 로 오버라이드 implements method 
	
	// 전처리
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		// application scope : 
		// 서버 종료 시 까지 유지되는 Servlet 내장 객체
		// - 서버 내에 딱 한 개만 존재 
		//    --> 모든 클라이언트가 공용으로 사용
		
		
		// DB에서 조회한 코드랑 이름을 application scope에 올릴 것임
		
		// Controller를 거치지 않고 바로 Service로 감
		
		// application scope 객체 얻어오기
		ServletContext application = request.getServletContext();
		
	
		//info 레벨 정보만 보여줌
		
		//application scope에 "categoryTypeList" 가 없을 경우
		if(application.getAttribute("categoryTypeList")==null) {
			
			log.info("CategoryTypeInterceptor - postHandle (전처리) 동작 실행");
			
			//categoryTypeList 조회 서비스 호출
			List<Map<String, Object>> categoryTypeList 
			= service.selectCategoryTypeList();
			
			// 조회 결과를 application scope에 추가
			application.setAttribute("categoryTypeList", categoryTypeList);
			

		}
		
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	// 후처리
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// ModelAndView Model 과 어디로 forward할 지
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

	
	
	
	
	
	
}
