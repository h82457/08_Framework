package edu.kh.project.common.config;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.kh.project.common.filter.LoginFilter;

/* 만들어놓은 Filter 클래스가 언제 적용될지 설정 */

@Configuration // 서버가 켜질때 해당 클래스 내 모든 메서드가 실행

public class FilterConfig {
	
	@Bean // 반환된 객체를 Bean으로 등록 (필터가 빈으로 등록)
	public FilterRegistrationBean<LoginFilter> loginFilter(){
		
		// FilterRegistrationBean : 필터를 Bean으로 등록하는 객체
		FilterRegistrationBean<LoginFilter> filter = new FilterRegistrationBean<>();
		
		// 사용할 필터 객체 추가, 마이페이지로 시작하는 모든 요청시 필터 실행
		filter.setFilter(new LoginFilter());
		String[] filteringURL = {"/myPage/*"}; // /myPage/* : myPage로 시작하는 모든 요청 
		
		// 필터가 동작할 URL을 세팅,Arrays.asList(filteringURL)-> filteringURL 배열을 List로 변환
		
		filter.setUrlPatterns(Arrays.asList(filteringURL));
		
		// 필터 이름 지정
		filter.setName("loginFilter");
		
		// 필터 순서 지정
		filter.setOrder(1);
		
		return filter; // 반환된 객체가 필터를 생성해서 Bean으로 등록
	}				// 		ㄴ> 요청시 필터가 작동
}
