package edu.kh.project.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import edu.kh.project.common.interceptor.BoardTypeInterceptor;

/* 인터셉터가 어떤 요청을 가로챌지 설정하는 클래스 <- 설정과 관련된 어노테이션 작성 필요 */

@Configuration // 서버가 켜지면 내부 메서드를 수행
public class InterceptorConfig implements WebMvcConfigurer{

	
	// 인터셉터 클래스 Bean 등록
	@Bean // 개발자가 만들어서 반환하는 객체를 Bean 등록, 관리는 Spring Container가 수행
	public BoardTypeInterceptor boardTypeInterceptor() { return new BoardTypeInterceptor(); }

	
	// 동작할 인터셉터를 객체를 추가하는 메서드
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		// Bean으로 등록된 boardTypeInterceptor를 얻어와 매개변수로 전달
		registry.addInterceptor( boardTypeInterceptor() )
		
		.addPathPatterns("/**")
//			ㄴ> 가로챌 요청 주소를 지정, 여러 주소 작성시 "", "" 콤마로 구분, /** : /이하 모든 요청 주소(모든 요청 주소를 가로챔)	
		
		.excludePathPatterns("/css/**", "/js/**", "/images/**", "/favicon.ico");
//			ㄴ > 가로채지 않을 요청 지정, 해당 요청 주소에서는 동작 X

	}
	
	
}
