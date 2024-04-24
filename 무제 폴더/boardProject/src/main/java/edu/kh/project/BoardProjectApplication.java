package edu.kh.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class BoardProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardProjectApplication.class, args);
	}
}

/* 서버 start
 * 
 * 	-> 각종 설정 읽음 (서버 기본 설정, 자동 설정, 사용자 설정<- DBCP등)
 * 		ㄴ + ComponentScan (Bean 등록 구문) <~ @Component, @Controller, @Service, @mapper
 * 
 * 	-> 클라이언트 요청 대기 상태
 * 
 * 	-> 클라이언트 요청 -> DispatcherServlet
 * 
 *	-> Controller <-> Service <-> DAO/Mapper <-> DB
 * 
 *  -> DispathcerSevlet
 *  	ㄴ 1) forward -> ViewResolver(접두사 + 반환값 + 접미사)
 *  	ㄴ 2) redirect -> 재요청한 Controller
 *  	ㄴ 3) 비동기 요청 -> 요청한 JS 코드
 *  
 *  -> 클라이언트 응답
 * 
 * */